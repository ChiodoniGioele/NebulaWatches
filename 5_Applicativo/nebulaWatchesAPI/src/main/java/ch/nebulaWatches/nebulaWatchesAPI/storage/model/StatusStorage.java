package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "status")
public class StatusStorage {
    @Id
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
