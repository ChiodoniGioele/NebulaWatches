package ch.nebulaWatches.nebulaWatchesAPI.watches.repository;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchRepository extends JpaRepository<Watch, String> {

}
