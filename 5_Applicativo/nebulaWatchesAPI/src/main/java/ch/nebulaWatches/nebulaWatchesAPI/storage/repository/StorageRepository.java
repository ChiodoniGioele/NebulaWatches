package ch.nebulaWatches.nebulaWatchesAPI.storage.repository;

import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {
    @Query("SELECT s FROM Storage s LEFT JOIN Watch w ON s.watch.reference = w.reference " +
            "LEFT JOIN CustomWatch cw ON s.customWatch.reference = cw.reference " +
            "WHERE s.user.id = ?1")
    List<Storage> findByUser(int userId);

    @Transactional
    void deleteById(Long id);

    int findQuantityById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Storage s SET s.quantity = ?1 WHERE id = ?2")
    void updateQuantityById(int quantity, Long id);
}
