package ch.nebulaWatches.nebulaWatchesAPI;

import ch.nebulaWatches.nebulaWatchesAPI.security.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class NebulaWatchesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NebulaWatchesApiApplication.class, args);
	}

}
