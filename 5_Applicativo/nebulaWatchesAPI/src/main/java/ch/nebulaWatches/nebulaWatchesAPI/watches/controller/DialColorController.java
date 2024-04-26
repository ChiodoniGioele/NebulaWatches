package ch.nebulaWatches.nebulaWatchesAPI.watches.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.DialColor;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.DialColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/dial_colors")
public class DialColorController {
    private final DialColorService dialColorService;

    @Autowired
    public DialColorController(DialColorService dialColorService) {
        this.dialColorService = dialColorService;
    }

    @GetMapping
    public List<DialColor> getDialColor(){
        return dialColorService.getDialColors();
    }
}