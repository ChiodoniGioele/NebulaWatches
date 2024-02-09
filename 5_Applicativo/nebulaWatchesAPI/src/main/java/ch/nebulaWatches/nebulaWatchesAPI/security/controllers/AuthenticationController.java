package ch.nebulaWatches.nebulaWatchesAPI.security.controllers;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.AuthenticationRequest;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.AuthenticationResponse;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.RegisterRequest;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
