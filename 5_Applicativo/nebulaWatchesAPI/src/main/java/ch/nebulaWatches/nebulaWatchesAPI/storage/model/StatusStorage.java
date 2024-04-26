package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "status")
@AllArgsConstructor
@NoArgsConstructor
public class StatusStorage {
    @Id
    private String name;

}
