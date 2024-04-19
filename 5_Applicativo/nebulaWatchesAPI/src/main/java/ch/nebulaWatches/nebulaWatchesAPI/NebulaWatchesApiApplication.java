package ch.nebulaWatches.nebulaWatchesAPI;

import ch.nebulaWatches.nebulaWatchesAPI.security.service.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NebulaWatchesApiApplication {

	public static void main(String[] args) {

/*		EmailService gEmailSender = new EmailService();
		String to = "gioele.chiodoni@gmail.com";
		String subject = "Second: Sending email using GMail";
		String text = "This is a example email send using gmail and java program with out less secure app";
		boolean b = gEmailSender.sendEmail(to, subject, text);

		if (b) {
			System.out.println("Email is sent successfully");
		} else {
			System.err.println("There is problem in sending email");
		}*/





		SpringApplication.run(NebulaWatchesApiApplication.class, args);
	}
}
