package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

import java.sql.Blob;

@Data
public class CustomWatchRequest {
    private String reference;
    private String name;
    private String description;
    private Float retailPrice;
    private Blob image;
    private int id;
}
