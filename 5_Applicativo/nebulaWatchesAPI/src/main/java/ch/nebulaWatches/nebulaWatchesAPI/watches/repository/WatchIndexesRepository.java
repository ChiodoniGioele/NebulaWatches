package ch.nebulaWatches.nebulaWatchesAPI.watches.repository;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Hands;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.WatchIndexes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchIndexesRepository extends JpaRepository<WatchIndexes, String> {
    List<WatchIndexes> findAllByNameIn(List<String> indexesNames);
}
