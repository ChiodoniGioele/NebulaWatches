package ch.nebulaWatches.nebulaWatchesAPI.storage.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/storage")
@RequiredArgsConstructor
public class StorageController {
    @GetMapping
    public List<Watch> getStorage(){
        return null;
    }
}
