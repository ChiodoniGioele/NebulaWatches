package ch.nebulaWatches.nebulaWatchesAPI.storage.repository;

import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Query("SELECT SUM(s.quantity) FROM Storage s WHERE s.team.id = ?1")
    int sumQuantityByTeam(Long teamId);

    //Query scritta in mysql e non in JPA a causa di date_sub()
    @Query(value = "SELECT SUM(s.quantity) FROM storage s WHERE s.team_id = ?1 AND s.sell_date > ?2 AND s.sell_date < ?3", nativeQuery = true)
    int sumQuantityByTeamMonth(Long teamId, LocalDate begin, LocalDate end);

}
