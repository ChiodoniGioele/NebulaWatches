package ch.nebulaWatches.nebulaWatchesAPI.security.service;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import ch.nebulaWatches.nebulaWatchesAPI.utils.InputUtils;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final EmailService emailService;

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

    public boolean isUserVerified(String email) {
        if(repository.isVerified(email).isPresent()){
            return repository.isVerified(email).get();
        }
        return false;
    }

    public boolean isUserPresent(String email) {
        return repository.isEmailUsed(email);
    }

    public void sendAgain(String email) {
        int code = repository.getCode(email);
        String text = "Hello," + " \n\r" + "To complete the registration process for your " +
                "account, please use the following PIN code: \n\r" + code + "\n\r \n\r Sincerely, \n\r NebulaWatches Team";
       emailService.sendEmail(InputUtils.testInput(email), "NebulaWatches Account Verification - Your PIN Code", text);
    }
}
