package ch.nebulaWatches.nebulaWatchesAPI.security.service;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {

    public void sendEmail(String email, int code) throws MailjetException {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient("b5eb50bd96fbbdb86da6f2bf2a1227ef", "464493d6ed8ffd5d4bea99743b544235");
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "nebulawatchesproject@gmail.com")
                                        .put("Name", "nebulawatchesproject@gmail.com"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", email)
                                                .put("Name", email)))
                                .put(Emailv31.Message.SUBJECT, "NebulaWatches Account Verification")
                                .put(Emailv31.Message.HTMLPART,
                                        "<title>Welcome to NebulaWatches</title><style>body {font-family: Arial, sans-serif;background-color: #f4f4f4;margin: 0;padding: 0;}  .container {max-width: 600px;margin: 50px auto;padding: 20px;background-color: #ffffff;border-radius: 10px;box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);}  .welcome-heading {text-align: center;color: #333333;}  .login-code-container {text-align: center;margin-top: 30px;}  .login-code {display: inline-block;padding: 10px 20px;background-color: #4CAF50;color: #ffffff;border-radius: 20px;font-size: 20px;}</style></head><body><div class='container'><h1 class='welcome-heading'>Welcome to NebulaWatches!</h1><p>Please complete your account verification with the code below:</p><div class='login-code-container'><div class='login-code'>"+code+"</div></div></div>"
                                )));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }
}
