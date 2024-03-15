package ch.nebulaWatches.nebulaWatchesAPI.security.models;

import lombok.Data;

@Data
public class AdminRequest {
    private int id;
    private String email;
    private String username;
    private String password;
    private boolean loginMode;
    private boolean verified;
}
