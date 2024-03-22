package ch.nebulaWatches.nebulaWatchesAPI.security.service;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.AdminRequest;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.Role;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User getUser(int id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setUsername(user1.getName());
            return user1;
        }
        return null;
    }
    public List<User> getAllUsers() {
        return repository.findAllUsersWithUserRole();
    }

    public Role getRole(String userEmail) {
        return repository.getRoleForUser(userEmail);
    }


    public void removeUser(AdminRequest request) {
        repository.deleteByEmail(request.getEmail());
    }

    public void saveUser(AdminRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setLoginMode(request.isLoginMode());
        user.setVerified(request.isVerified());
        repository.save(user);
    }
    public void updateUser(AdminRequest request){
        Optional<User> user = repository.findById(request.getId());
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setId(request.getId());
            user1.setUsername(request.getUsername());
            user1.setEmail(request.getEmail());
            user1.setPassword(passwordEncoder.encode(request.getPassword()));
            user1.setRole(Role.USER);
            user1.setLoginMode(request.isLoginMode());
            user1.setVerified(request.isVerified());
            repository.save(user1);
        }
    }
    public List<User> searchUsers(String searchContent) {
        return repository.findUsersBySearch(searchContent);
    }
}

