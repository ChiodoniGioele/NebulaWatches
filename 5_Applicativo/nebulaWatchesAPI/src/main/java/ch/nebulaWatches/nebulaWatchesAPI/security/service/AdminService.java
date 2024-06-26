package ch.nebulaWatches.nebulaWatchesAPI.security.service;

import ch.nebulaWatches.nebulaWatchesAPI.utils.InputUtils;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.AdminRequest;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.Role;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import com.mailjet.client.errors.MailjetException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final EmailService emailService;

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

    public Page<User> getAllUsers(int page, int pageLength, String sortBy) {
        if (page < 0 || pageLength <= 0) {
            throw new IllegalArgumentException("Invalid page or length parameters");
        }
        Sort.Direction sortDirection = Sort.Direction.DESC;
        return repository.findAllUsersWithUserRole(PageRequest.of(page, pageLength, sortDirection, sortBy));

    }

    public Role getRole(String userEmail) {
        return repository.getRoleForUser(userEmail);
    }


    public void removeUser(AdminRequest request) {
        repository.deleteByEmail(request.getEmail());
    }
    public void archive(AdminRequest request) {
        repository.setArchivedTrue(request.getEmail());
    }

    public void saveUser(AdminRequest request) {
        User user = new User();
        int code = -1;
        if(!request.isVerified()){
            code = (int) (100000 + Math.random() * 900000);
            try {
                emailService.sendEmail(InputUtils.testInput(request.getEmail()), code);
            }catch (Exception e){
                System.err.println(e);
            }

        }
        user.setCode(code);
        user.setUsername(InputUtils.testInput(request.getUsername()));
        user.setEmail(InputUtils.testInput(request.getEmail()));
        user.setPassword(passwordEncoder.encode(InputUtils.testInput(request.getPassword())));
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
            user1.setUsername(InputUtils.testInput(request.getUsername()));
            user1.setEmail(InputUtils.testInput(request.getEmail()));
            user1.setPassword(passwordEncoder.encode(InputUtils.testInput(request.getPassword())));
            user1.setRole(Role.USER);
            user1.setLoginMode(request.isLoginMode());
            user1.setVerified(request.isVerified());
            repository.save(user1);
        }
    }
    public List<User> searchUsers(String searchContent) {
        return repository.findUsersBySearch(searchContent);
    }

    public boolean isAdminByToken(String token){
        String payload = new String(Base64.getDecoder().decode(token.split("\\.")[1]));
        String mail = payload.split("\"")[3];
        User user = userService.getUserByEmail(mail);
        return user.getRole().equals(Role.ADMIN);
    }

    public boolean isEmailUsed(String mail) {
        return repository.isEmailUsed(mail);
    }
}

