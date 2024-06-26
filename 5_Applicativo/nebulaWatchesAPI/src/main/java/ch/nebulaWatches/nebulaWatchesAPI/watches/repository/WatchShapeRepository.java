package ch.nebulaWatches.nebulaWatchesAPI.watches.repository;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Hands;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.WatchShape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchShapeRepository extends JpaRepository<WatchShape, String> {
    List<WatchShape> findAllByNameIn(List<String> shapeNames);
}
