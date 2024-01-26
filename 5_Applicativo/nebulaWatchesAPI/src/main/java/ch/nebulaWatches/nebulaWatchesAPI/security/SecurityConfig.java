package ch.nebulaWatches.nebulaWatchesAPI.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                c -> c.requestMatchers("home").access(new WebExpressionAuthorizationManager("isAutenticated"))
                        .anyRequest().authenticated()
        );

        http.csrf(c -> c.ignoringRequestMatchers("/some/endpoints/**"));

        http.cors(c -> c.configurationSource( req -> {
            CorsConfiguration conf = new CorsConfiguration();
            conf.setAllowedMethods(List.of("*"));
            return conf;
        }));

        return http.build();

    }

}
