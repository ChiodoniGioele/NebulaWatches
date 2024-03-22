package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import lombok.Data;

@Data
public class StorageRequest {
    private long id;
    private String user_email;
    private String watch_reference;
    private String status;
    private int quantity;
    private String custom_watch_reference;
    private float buyPrice;
    private float sellPrice;
    private Long clientId;
    private Long teamId;

}
