package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import lombok.Data;

@Data
public class FavouriteRequest {
    private String user_email;
    private String watch_reference;
}