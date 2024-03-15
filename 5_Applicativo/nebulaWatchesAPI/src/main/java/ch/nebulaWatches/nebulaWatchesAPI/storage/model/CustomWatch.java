package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@Entity
@Table(name = "custom_watch")
@AllArgsConstructor
@NoArgsConstructor
public class CustomWatch {
    @Id
    private String reference;
    private String name;
    @Column(length = 2500)
    private String description;
    private Float retailPrice;
    @Lob
    private Blob image;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
