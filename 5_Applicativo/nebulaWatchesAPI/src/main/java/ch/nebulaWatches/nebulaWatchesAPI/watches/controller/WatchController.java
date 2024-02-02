package ch.nebulaWatches.nebulaWatchesAPI.watches.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.dto.WatchDTO;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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
        return watchService.getWatchDTO(watchReference);
    }

    @GetMapping(path = "{watchReference}/image")
    public byte[] getWatchImage(@PathVariable("watchReference") String watchReference){
        Optional<Watch> watch = watchService.getWatch(watchReference);

        if (watch.isPresent()) {
            try{
                return watch.get().getImageBytes();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new byte[0];
        }
    }
}
