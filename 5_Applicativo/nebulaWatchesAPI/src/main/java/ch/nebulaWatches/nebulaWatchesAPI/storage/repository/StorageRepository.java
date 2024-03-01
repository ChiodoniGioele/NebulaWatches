package ch.nebulaWatches.nebulaWatchesAPI.storage.repository;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {
    @Query("SELECT s FROM Storage s WHERE s.user.id.name = ?1")
    List<Watch> findByUser(int userId);

}
