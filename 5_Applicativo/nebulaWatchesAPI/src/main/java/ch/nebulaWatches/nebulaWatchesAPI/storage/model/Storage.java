package ch.nebulaWatches.nebulaWatchesAPI.storage.model;

import ch.nebulaWatches.nebulaWatchesAPI.clients.model.Client;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;

import ch.nebulaWatches.nebulaWatchesAPI.team.model.Team;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "custom_watch_reference")
    private CustomWatch customWatch;

    @JoinColumn(name = "buy_price")
    private float buyPrice;

    @JoinColumn(name = "sell_price", nullable = true)
    private float sellPrice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    @JoinColumn(name = "purchase_date")
    private Date purchaseDate;

    @JoinColumn(name = "sell_date")
    private Date sellDate;
}
