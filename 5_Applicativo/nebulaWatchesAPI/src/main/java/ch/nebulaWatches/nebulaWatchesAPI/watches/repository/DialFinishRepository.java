package ch.nebulaWatches.nebulaWatchesAPI.watches.repository;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.DialFinish;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DialFinishRepository extends JpaRepository<DialFinish, String> {
    List<DialFinish> findAllByNameIn(List<String> dialFinishNames);
}
