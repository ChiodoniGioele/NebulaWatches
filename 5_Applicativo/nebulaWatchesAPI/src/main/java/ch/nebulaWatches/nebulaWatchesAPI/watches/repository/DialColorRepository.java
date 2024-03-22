package ch.nebulaWatches.nebulaWatchesAPI.watches.repository;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.DialColor;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialColorRepository extends JpaRepository<DialColor, String> {
    List<DialColor> findAllByNameIn(List<String> dialColorNames);
}
