package ch.nebulaWatches.nebulaWatchesAPI.security.controllers;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.AuthenticationRequest;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.AuthenticationResponse;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.RegisterRequest;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.AuthenticationService;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.JwtService;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.UserService;
import ch.nebulaWatches.nebulaWatchesAPI.utils.InputUtils;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.WatchIndexes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/isTokenValid")
    public ResponseEntity<String> isTokenValid(@RequestParam(required = true) String jwt) {
        try {
            return ResponseEntity.ok(jwtService.isTokenValid(jwt));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PostMapping("/verify")
    public ResponseEntity<AuthenticationResponse> verifyEmail(@RequestParam("code") int code, @RequestParam("email") String email) {
        return ResponseEntity.ok(service.verifyEmail(code, email));
    }

    @GetMapping("/isVerified/{email}")
    public boolean isVerified(@PathVariable String email){
        return userService.isUserVerified(email);
    }
    @GetMapping("/exists/{email}")
    public boolean existsEmail(@PathVariable String email){
        return userService.isUserPresent(email);
    }
    @GetMapping("/sendAgain")
    public void send(@RequestParam("email") String email){
        userService.sendAgain(email);

    }

}
