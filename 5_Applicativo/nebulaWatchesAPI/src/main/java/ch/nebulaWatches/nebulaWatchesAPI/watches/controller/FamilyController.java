package ch.nebulaWatches.nebulaWatchesAPI.watches.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.exceptions.WatchNotFoundException;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Family;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.FamilyService;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping(path = "/v1/families")
public class FamilyController {
    private final FamilyService familyService;
    private final WatchService watchService;
    private Random rand;

    @Autowired
    public FamilyController(FamilyService familyService, WatchService watchService) {
        this.familyService = familyService;
        this.watchService = watchService;
        rand = new Random();
    }

    @GetMapping
    public List<Family> getFamilies(){
        return familyService.getFamilies();
    }

    @GetMapping(path = "{familyId}/rndimage")
    public ResponseEntity<byte[]> getFamilyRandomImage(@PathVariable("familyId") int familyId) {
        try {
            List<Watch> watches = watchService.getWatchesByFamily(familyId);
            Watch randomChoice = watches.get(rand.nextInt(0, watches.size()));
            String randomWatchReference = randomChoice.getReference();
            byte[] imageBytes = watchService.getWatchImageBytes(randomWatchReference);
            return ResponseEntity.ok(imageBytes);
        } catch (WatchNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "{familyId}/watches")
    public List<Watch> getWatchesByFamily(@PathVariable("familyId") int familyId){
        return watchService.getWatchesByFamily(familyId);
    }


}
