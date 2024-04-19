package ch.nebulaWatches.nebulaWatchesAPI.storage.repository;

import ch.nebulaWatches.nebulaWatchesAPI.clients.model.Client;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Storage;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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


    List<Storage> getByTeamId(Long id);

    @Query("SELECT s FROM Storage s WHERE (s.watch.reference = ?1 OR s.customWatch.reference = ?1) AND s.user.id = ?2 AND s.purchaseDate = ?3 AND s.buyPrice = ?4 AND s.status.name = ?5")
    Optional<Storage> getByRequest(String reference, int userId, Date date, float buyPrice, String status);

    @Query("SELECT s FROM Storage s WHERE (s.watch.reference = ?1 OR s.customWatch.reference = ?1) AND s.user.id = ?2 AND s.purchaseDate = ?3 AND s.buyPrice = ?4 AND s.status.name = ?5 AND s.sellPrice = ?6 AND s.sellDate = ?7 AND s.team.id = ?8 AND s.client.id = ?9")
    Optional<Storage> getByRequestSold(String reference, int userId, Date date, float buyPrice, String status, float sellPrice, Date date2, Long teamId, Long clientId);


    @Query(value = "SELECT SUM(s.quantity) FROM Storage s WHERE s.client_id = ?1 AND s.sell_date > ?2 AND s.sell_date < ?3 and s.status_name = ?4", nativeQuery = true)
    Optional<Integer> sumQuantityByClientMonth(Long clientId, LocalDate begin, LocalDate end, String status);


    @Query("SELECT s FROM Storage s WHERE s.user.email = ?1 and s.client.id = ?2 and s.status.name = ?3")
    List<Storage> findByUserEmailAndClientIdAndStatus(String email, long id, String status);

}
