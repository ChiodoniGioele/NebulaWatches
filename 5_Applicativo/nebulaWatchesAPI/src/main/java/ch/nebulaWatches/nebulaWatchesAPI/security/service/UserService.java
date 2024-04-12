package ch.nebulaWatches.nebulaWatchesAPI.security.service;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    public String getUsername(String email) {
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            return user.get().getName();
        }else{
            return "Username";
        }
    }
    public int getId(String email) {
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            return user.get().getId();
        }else{
            return 0;
        }
    }
    public User getUser(int id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setUsername(user1.getName());
            return user1;
        }
        return null;
    }
    public User getUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
