package ch.nebulaWatches.nebulaWatchesAPI.team.model;

import lombok.Data;

@Data
public class TeamRequest {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String role;

    private String userEmail;
}
