package ch.nebulaWatches.nebulaWatchesAPI.clients.model;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ClientRequest {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String notes;

    private String userEmail;

}
