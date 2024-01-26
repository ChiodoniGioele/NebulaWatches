package ch.nebulaWatches.nebulaWatchesAPI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWordController {

    @GetMapping("/hi")
    public String hi(){
        return "Hi";
    }
}
