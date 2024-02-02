package ch.nebulaWatches.nebulaWatchesAPI.security.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(Authentication authentication2) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

       // DefaultOidcUser principal = (DefaultOidcUser) authentication2.getPrincipal();
       // var email = principal.getAttribute("email");

        return String.format("Hello %s", currentPrincipalName);
    }
}
