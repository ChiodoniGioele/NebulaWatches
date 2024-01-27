package ch.nebulaWatches.nebulaWatchesAPI.watches.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.dto.WatchDTO;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/watches")
public class WatchController {
    private final WatchService watchService;

    @Autowired
    public WatchController(WatchService watchService) {
        this.watchService = watchService;
    }

    @GetMapping
    public List<WatchDTO> getWatches(){
        return watchService.getWatches();
    }

    @GetMapping(path = "{watchReference}")
    public Optional<WatchDTO> getWatch(@PathVariable("watchReference") String watchReference){
        return watchService.getWatch(watchReference);
    }
}
