package ch.nebulaWatches.nebulaWatchesAPI.watches.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Hands;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.HandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/hands")
public class HandsController {
    private final HandsService handsService;

    @Autowired
    public HandsController(HandsService handsService) {
        this.handsService = handsService;
    }

    @GetMapping
    public List<Hands> getHands(){
        return handsService.getHands();
    }
}