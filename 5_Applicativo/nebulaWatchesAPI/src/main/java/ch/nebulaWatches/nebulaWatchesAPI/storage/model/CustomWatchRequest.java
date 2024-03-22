package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;

@Data
public class CustomWatchRequest {
    private String reference;
    private String name;
    private String description;
    private Float retailPrice;
    private String email;
    private byte[] image;
    //private Blob image;
    //private MultipartFile image;
}
