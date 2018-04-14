package tdl.record_upload.upload;

import lombok.extern.slf4j.Slf4j;
import tdl.s3.sync.Filters;
import tdl.s3.sync.RemoteSync;
import tdl.s3.sync.Source;
import tdl.s3.sync.destination.Destination;
import tdl.s3.sync.progress.UploadStatsProgressListener;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class BackgroundRemoteSyncTask {
    private final Timer syncTimer;
    private Lock syncLock;
    private final RemoteSync remoteSync;

    public BackgroundRemoteSyncTask(String localStorageFolder,
                             Destination remoteDestination,
                             UploadStatsProgressListener uploadStatsProgressListener) {
        Filters filters = Filters.getBuilder()
                .include(Filters.endsWith(".mp4"))
                .include(Filters.endsWith(".log"))
                .include(Filters.endsWith(".srcs"))
                .create();
        Source localFolder = Source.getBuilder(Paths.get(localStorageFolder))
                .setFilters(filters)
                .create();

        remoteSync = new RemoteSync(localFolder, remoteDestination);
        remoteSync.setListener(uploadStatsProgressListener);

        syncTimer = new Timer("Upload");
        syncLock = new ReentrantLock();
    }

    public void scheduleSyncEvery(Duration delayBetweenRuns) {
        syncTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                boolean shouldSync = syncLock.tryLock();
                if (shouldSync) {
                    try {
                        log.info("Sync local files with remote");
                        remoteSync.run();
                    } catch (Exception e) {
                        log.warn("Remote sync failed. Will retry later.", e);
                    } finally {
                        syncLock.unlock();
                    }
                } else {
                    log.info("Sync already in progress. Skipping");
                }
            }
        }, 0, delayBetweenRuns.toMillis());
    }

    public void finalRun() {
        log.info("Upload remaining parts and finalise video");
        syncLock.lock();
        try {
            remoteSync.run();
        } catch (Exception e) {
            log.error("File upload failed. Some files might not have been uploaded. Reason: ", e);
        } finally {
            syncLock.unlock();
        }
    }
}
