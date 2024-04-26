package ch.nebulaWatches.nebulaWatchesAPI.watches.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.WatchShape;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.WatchShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/watch_shapes")
public class WatchShapeController {
    private final WatchShapeService watchShapeService;

    @Autowired
    public WatchShapeController(WatchShapeService watchShapeService) {
        this.watchShapeService = watchShapeService;
    }

    @GetMapping
    public List<WatchShape> getWatchShapes(){
        return watchShapeService.getWatchShapes();
    }
}