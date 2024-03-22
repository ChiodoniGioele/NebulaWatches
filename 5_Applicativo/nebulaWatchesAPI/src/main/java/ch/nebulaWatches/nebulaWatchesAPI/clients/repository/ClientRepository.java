package ch.nebulaWatches.nebulaWatchesAPI.clients.repository;

import ch.nebulaWatches.nebulaWatchesAPI.clients.model.Client;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "select c from Client c where c.user.email = ?1")
    List<Client> findAllByUserId(String email);

    @Query(value = "select c from Client c where c.user.email = ?1 and c.id = ?2")
    Optional<Client> findByClientId(String email, Long id);


    @Query(value = "UPDATE Client c SET c.name = ?2, c.surname = ?3, c.email = ?4, c.notes = ?5 WHERE c.id = ?1 and c.user.email = ?6")
    void updateCLient(Long id, String name, String surname, String email, String notes, String userEmail);


    @Query(value = "delete from Client c where c.user.email = ?1 and c.id = ?2")
    void deleteClientById(String email, Long id);
}