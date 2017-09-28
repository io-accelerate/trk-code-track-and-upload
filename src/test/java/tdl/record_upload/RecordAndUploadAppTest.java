package tdl.record_upload;

import org.junit.Ignore;
import org.junit.Test;

public class RecordAndUploadAppTest {

    /**
     * Start the app
     *
     * Check output, you should see:
     *  - recording metrics for frames
     *  - once every 3 minutes you see a snapshot being taken
     *  - once every 5 minutes you should see a SyncTask uploading files
     *
     * Stop the recording (CTRL+C), you should see:
     *  - a log message saying that the screen recording is stopping
     *  - a log message saying that the source code recording is stopping
     *  - upload messages for the video and the log file
     *
     * Download the sourceStream file, and use the cli tool to:
     *  - list the snapshots
     *  - convert the file to a git repo
     */
    @Ignore("Manual acceptance")
    @Test
    public void record_and_upload() throws Exception {
    }


    /**
     *
     * Use Minio as S3 alternative
     * Prepare the localSource folder
     *
     * Start the app
     *
     * Check storage folder, you should see:
     *  - a video file with timestamp plus a corresponding .lock file
     *  - a sourceStream file with timestamp plus a corresponding .lock file
     *  - a log file plus a corresponding .lock file
     *
     * Stop the recording (CTRL+C)
     *
     * Check storage folder, you should see:
     *  - no .lock file
     *
     * Check AWS S3, you should see:
     *  - a video file
     *  - a sourceStream file
     *  - a log file
     *
     * Compare local video with remote video:
     *  - compute the md5sum of local video
     *  - download remote video and compute md5sum
     *  - md5 should match
     *
     * Visually inspect the remote video, check if:
     *  - the video quality is ok
     *  - the playback speed is 4x
     *
     * Download the sourceStream file, and use the cli tool to:
     *  - list the snapshots
     *  - convert the file to a git repo
     *
     * Check the logs:
     *  - you should see Starting messages, Metrics, Upload
     */

    @Ignore("TODO write the test")
    @Test
    public void record_and_upload_X() throws Exception {
    }
}