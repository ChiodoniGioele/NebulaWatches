package ch.nebulaWatches.nebulaWatchesAPI.watches.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.WatchIndexes;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.WatchShape;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.WatchIndexesService;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.WatchShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/watch_indexes")
public class WatchIndexesController {
    private final WatchIndexesService watchIndexesService;

    @Autowired
    public WatchIndexesController(WatchIndexesService watchIndexesService) {
        this.watchIndexesService = watchIndexesService;
    }

    @GetMapping
    public List<WatchIndexes> getWatchShapes(){
        return watchIndexesService.getWatchIndexes();
    }
}