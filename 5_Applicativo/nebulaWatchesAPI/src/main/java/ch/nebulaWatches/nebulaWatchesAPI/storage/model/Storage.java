package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "storage")
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "watch_reference")
    private Watch watch;

    @ManyToOne
    @JoinColumn(name = "status_name")
    private StatusStorage status;



}
