package ch.nebulaWatches.nebulaWatchesAPI.storage.repository;

import ch.nebulaWatches.nebulaWatchesAPI.storage.model.CustomWatch;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Favourite;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomWatchRepository extends JpaRepository<CustomWatch, Integer> {
    @Query("SELECT c FROM CustomWatch c WHERE c.user.id = ?1")
    List<CustomWatch> findByUser(int userId);

    Optional<CustomWatch> findByReference(String reference);

    @Transactional
    void deleteByReference(String reference);
}
