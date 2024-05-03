package ch.nebulaWatches.nebulaWatchesAPI.security.service;

import ch.nebulaWatches.nebulaWatchesAPI.utils.InputUtils;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.*;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import com.mailjet.client.errors.MailjetException;
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
    private final EmailService emailService;

    public AuthenticationResponse register(RegisterRequest request) {
        if (InputUtils.isEmailValid(request.getEmail())) {
            int verCode = (int) (100000 + Math.random() * 900000);
            var user = User.builder()
                    .username(InputUtils.testInput(request.getUsername()))
                    .email(InputUtils.testInput(request.getEmail()))
                    .password(passwordEncoder.encode(InputUtils.testInput(request.getPassword())))
                    .role(Role.USER)
                    .verified(false)
                    .code(verCode)
                    .build();
            repository.save(user);
            try {
                emailService.sendEmail(InputUtils.testInput(request.getEmail()), verCode);
            }catch (Exception e ){
                System.err.println(e);
            }


            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        return null;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (InputUtils.isEmailValid(request.getEmail()) && !repository.isArchived(request.getEmail()).get()) {
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

    public AuthenticationResponse verifyEmail(int code, String email) {
        if(code >= 100000 && code <= 999999 ){
            User user = repository.findByCodeAndEmail(code, email)
                    .orElseThrow(() -> new RuntimeException("Invalid verification code"));
            user.setVerified(true);
            user.setCode(-1);
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }else{
            throw new RuntimeException("Invalid verification code");
        }

    }
}
