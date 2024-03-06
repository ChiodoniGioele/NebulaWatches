package ch.nebulaWatches.nebulaWatchesAPI;

import ch.nebulaWatches.nebulaWatchesAPI.security.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication()
// exclude = {SecurityAutoConfiguration.class, OAuth2ClientAutoConfiguration.class}
@EnableConfigurationProperties({RsaKeyProperties.class})
public class NebulaWatchesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NebulaWatchesApiApplication.class, args);
	}

}
