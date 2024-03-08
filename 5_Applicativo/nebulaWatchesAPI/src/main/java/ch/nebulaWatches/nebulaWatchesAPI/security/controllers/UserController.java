package ch.nebulaWatches.nebulaWatchesAPI.security.controllers;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.AuthenticationService;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getName")
    public String getUser(@RequestParam String email) {
        return userService.getUsername(email);
    }
}
