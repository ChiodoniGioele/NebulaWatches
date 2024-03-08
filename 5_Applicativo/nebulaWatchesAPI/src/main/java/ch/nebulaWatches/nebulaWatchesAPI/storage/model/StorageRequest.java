package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import lombok.Data;

@Data
public class StorageRequest {
    private String user_email;
    private String watch_reference;
    private String status;

}
