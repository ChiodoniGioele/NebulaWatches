package ch.nebulaWatches.nebulaWatchesAPI.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {
    @GetMapping("/login")
    public ModelAndView toIndex() {
        return new ModelAndView("redirect:/index.html");
    }

}
