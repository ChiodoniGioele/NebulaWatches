package ch.nebulaWatches.nebulaWatchesAPI.storage.repository;

import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {
    @Query("SELECT s FROM Storage s JOIN Watch w ON s.watch.reference = w.reference WHERE s.user.id = ?1")
    List<Storage> findByUser(int userId);

    @Transactional
    void deleteById(Long id);
}
