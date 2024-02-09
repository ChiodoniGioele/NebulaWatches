package ch.nebulaWatches.nebulaWatchesAPI.security.repository;

import java.util.Optional;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}

