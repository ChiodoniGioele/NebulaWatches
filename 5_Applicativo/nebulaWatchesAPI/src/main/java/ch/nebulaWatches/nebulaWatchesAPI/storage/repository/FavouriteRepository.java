package ch.nebulaWatches.nebulaWatchesAPI.storage.repository;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Favourite;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Storage;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {
    @Query("SELECT w FROM Watch w JOIN Favourite f ON f.watch.reference = w.reference WHERE f.user.id = ?1")
    List<Watch> findByUser(int userId);

    @Transactional
    void deleteByUserAndWatch(User user, Watch watch);

    boolean existsByUserAndWatch(User user, Watch watch);

}
