package ch.nebulaWatches.nebulaWatchesAPI.watches.repository;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Hands;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Integer> {
}
