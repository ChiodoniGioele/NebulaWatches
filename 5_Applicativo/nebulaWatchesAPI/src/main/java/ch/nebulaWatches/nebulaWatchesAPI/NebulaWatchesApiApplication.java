package ch.nebulaWatches.nebulaWatchesAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication
public class NebulaWatchesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NebulaWatchesApiApplication.class, args);
	}

}
