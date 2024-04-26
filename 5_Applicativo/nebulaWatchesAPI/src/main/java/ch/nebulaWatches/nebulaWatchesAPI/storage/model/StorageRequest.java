package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import jakarta.persistence.JoinColumn;
import lombok.Data;

import java.util.Date;

@Data
public class StorageRequest {
    private long id;
    private String user_email;
    private String watch_reference;
    private String status;
    private int quantity;
    private String custom_watch_reference;
    private float buyPrice;
    private float sellPrice;
    private Long clientId;
    private Long teamId;
    private Date purchaseDate;
    private Date sellDate;


}
