package ch.nebulaWatches.nebulaWatchesAPI.storage.repository;

import ch.nebulaWatches.nebulaWatchesAPI.storage.model.CustomWatch;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Favourite;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomWatchRepository extends JpaRepository<CustomWatch, Integer> {
    @Query("SELECT c FROM CustomWatch c WHERE c.user.id = ?1")
    List<CustomWatch> findByUser(int userId);
}
