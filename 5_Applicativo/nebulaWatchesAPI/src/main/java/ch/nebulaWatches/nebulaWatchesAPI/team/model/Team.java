package ch.nebulaWatches.nebulaWatchesAPI.team.model;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "team")
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String phone;
    private String role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
