package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "status")
@AllArgsConstructor
public class StatusStorage {
    @Id
    private String name;

}
