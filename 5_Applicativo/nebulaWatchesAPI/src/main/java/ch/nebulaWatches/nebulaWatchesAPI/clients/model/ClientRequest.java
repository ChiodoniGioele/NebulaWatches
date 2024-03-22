package ch.nebulaWatches.nebulaWatchesAPI.clients.model;

import lombok.Data;

@Data
public class ClientRequest {
    private String user_email;
    private Client client;
}
