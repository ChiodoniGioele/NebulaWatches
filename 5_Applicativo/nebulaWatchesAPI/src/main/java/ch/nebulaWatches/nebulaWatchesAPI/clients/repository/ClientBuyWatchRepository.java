package ch.nebulaWatches.nebulaWatchesAPI.clients.repository;

import ch.nebulaWatches.nebulaWatchesAPI.clients.model.Client;
import ch.nebulaWatches.nebulaWatchesAPI.clients.model.ClientBuyWatch;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Family;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientBuyWatchRepository extends JpaRepository<ClientBuyWatch, Long> {

    @Query("SELECT b FROM ClientBuyWatch b WHERE b.client.id = :idClient")
    List<ClientBuyWatch> findByClientId(Long idClient);
}
