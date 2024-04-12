package ch.nebulaWatches.nebulaWatchesAPI.security.service;

import ch.nebulaWatches.nebulaWatchesAPI.utils.InputUtils;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.*;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        if (InputUtils.isEmailValid(request.getEmail())) {
            var user = User.builder()
                    .username(InputUtils.testInput(request.getUsername()))
                    .email(InputUtils.testInput(request.getEmail()))
                    .password(passwordEncoder.encode(InputUtils.testInput(request.getPassword())))
                    .role(Role.USER)
                    .build();
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        return null;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (InputUtils.isEmailValid(request.getEmail())) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        InputUtils.testInput(request.getEmail()),
                        InputUtils.testInput(request.getPassword())
                )
        );
        var user = repository.findByEmail(InputUtils.testInput(request.getEmail())).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
        return null;
    }
}
