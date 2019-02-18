package tdl.record_upload.upload;

import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.UploadPartRequest;
import tdl.s3.sync.destination.Destination;
import tdl.s3.sync.destination.DestinationOperationException;
import tdl.s3.upload.MultipartUploadResult;

import java.util.Collections;
import java.util.List;

public class NoOpDestination implements Destination {
    @Override
    public void startS3SyncSession() {
        // All good
    }

    @Override
    public void stopS3SyncSession() {
        // All good
    }

    @Override
    public List<String> filterUploadableFiles(List<String> paths) {
        return Collections.emptyList();
    }

    @Override
    public String initUploading(String remotePath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PartListing getAlreadyUploadedParts(String remotePath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MultipartUploadResult uploadMultiPart(UploadPartRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void commitMultipartUpload(String remotePath, List<PartETag> eTags, String uploadId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UploadPartRequest createUploadPartRequest(String remotePath) {
        throw new UnsupportedOperationException();
    }
}
