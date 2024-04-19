package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import lombok.Data;

@Data
public class BuysClientRequest {
    private String userEmail;
    private Long clientId;

}
