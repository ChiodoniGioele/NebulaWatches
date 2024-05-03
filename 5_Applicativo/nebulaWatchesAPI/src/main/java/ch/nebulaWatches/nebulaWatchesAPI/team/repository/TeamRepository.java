package ch.nebulaWatches.nebulaWatchesAPI.team.repository;

import ch.nebulaWatches.nebulaWatchesAPI.clients.model.Client;
import ch.nebulaWatches.nebulaWatchesAPI.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query(value = "select t from Team t where t.user.email = ?1 and t.status = true")
    List<Team> findAllByUserEmail(String email);

    @Transactional
    void deleteById(Long id);

}