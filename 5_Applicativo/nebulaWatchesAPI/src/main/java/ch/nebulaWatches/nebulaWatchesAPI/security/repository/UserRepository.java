package ch.nebulaWatches.nebulaWatchesAPI.security.repository;

import java.util.List;
import java.util.Optional;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.Role;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = 'USER' AND u.archived = false")
    List<User> findAllUsersWithUserRole();

    @Query("SELECT role FROM User WHERE email = ?1")
    Role getRoleForUser(String userEmail);

    @Transactional
    void deleteByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = 'USER' AND u.archived = false AND (u.username LIKE CONCAT('%', :search, '%') OR " +
            "u.email LIKE CONCAT('%', :search, '%'))")
    List<User> findUsersBySearch(String search);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.archived = true WHERE u.email = ?1")
    void setArchivedTrue(String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = ?1")
    boolean isEmailUsed(String email);

}

