package ch.nebulaWatches.nebulaWatchesAPI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogInController {
    @GetMapping("/")
    public String Hi(){
        return "Hi";
    }
    @GetMapping("/restricted")
    public String restricted(){
        return "not logged in";
    }
}
