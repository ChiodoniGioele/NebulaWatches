package ch.nebulaWatches.nebulaWatchesAPI.clients.model;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Storage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "clientBuyWatch")
@AllArgsConstructor
@NoArgsConstructor
public class ClientBuyWatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;

    private double price;

    private Date date;

    @Override
    public String toString() {
        return "ClientBuyWatch{" +
                "id=" + id +
                ", client=" + client +
                ", storage=" + storage +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
