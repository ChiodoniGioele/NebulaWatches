package ch.nebulaWatches.nebulaWatchesAPI.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {
    @GetMapping("/")
    public ModelAndView toIndex() {
        return new ModelAndView("redirect:/index.html");
    }

    @GetMapping("/login/1")
    public ModelAndView toLogin1() {
        return new ModelAndView("redirect:/login1.html");
    }
}
