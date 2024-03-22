package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "storage")
@AllArgsConstructor
@NoArgsConstructor
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "watch_reference")
    private Watch watch;

    @ManyToOne
    @JoinColumn(name = "status_name")
    private StatusStorage status;

    @ManyToOne
    @JoinColumn(name = "custom_watch_reference")
    private CustomWatch customWatch;
}
