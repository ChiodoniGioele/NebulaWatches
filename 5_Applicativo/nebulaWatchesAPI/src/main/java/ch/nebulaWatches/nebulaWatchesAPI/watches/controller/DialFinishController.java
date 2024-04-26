package ch.nebulaWatches.nebulaWatchesAPI.watches.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.DialFinish;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.DialFinishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/dial_finishes")
public class DialFinishController {
    private final DialFinishService dialFinishService;

    @Autowired
    public DialFinishController(DialFinishService dialFinishService) {
        this.dialFinishService = dialFinishService;
    }

    @GetMapping
    public List<DialFinish> getDialFinishes(){
        return dialFinishService.getDialFinishes();
    }
}