package ch.nebulaWatches.nebulaWatchesAPI.security.controller;

import ch.nebulaWatches.nebulaWatchesAPI.security.model.RegisterRequest;
import ch.nebulaWatches.nebulaWatchesAPI.security.model.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService tokenService;
    private final UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthService tokenService, UserRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @GetMapping("/api/user")
    public AuthService.Access user(Authentication authentication) {
        return tokenService.access(authentication);
    }

    @PostMapping("/register")
    public AuthService.Access register(@RequestBody RegisterRequest request) {
        User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()), "ADMIN");
        repository.save(user);
        return  tokenService.register(request);
    }

}