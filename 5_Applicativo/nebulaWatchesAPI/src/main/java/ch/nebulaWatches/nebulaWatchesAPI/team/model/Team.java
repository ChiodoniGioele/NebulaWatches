package ch.nebulaWatches.nebulaWatchesAPI.team.model;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Storage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /*@OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Storage> storageList = new ArrayList<>();*/
}
