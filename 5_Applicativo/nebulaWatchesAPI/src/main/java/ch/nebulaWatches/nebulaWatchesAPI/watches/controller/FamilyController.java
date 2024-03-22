package ch.nebulaWatches.nebulaWatchesAPI.watches.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.dto.WatchDTO;
import ch.nebulaWatches.nebulaWatchesAPI.watches.exceptions.WatchNotFoundException;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Brand;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Family;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.FamilyService;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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

    /*
    @GetMapping
    public List<Family> getFamilies(){
        return familyService.getFamilies();
    }
    */

    @GetMapping(path = "{familyId}/rndimage")
    public ResponseEntity<byte[]> getFamilyRandomImage(@PathVariable("familyId") int familyId) {
        try {
            List<WatchDTO> watches = watchService.getWatchesByFamily(familyId);
            WatchDTO randomChoice = watches.get(rand.nextInt(0, watches.size()));
            String randomWatchReference = randomChoice.getReference();
            byte[] imageBytes = watchService.getWatchImageBytes(randomWatchReference);
            return ResponseEntity.ok(imageBytes);
        } catch (WatchNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*
    @GetMapping(path = "{familyId}/watches")
    public List<Watch> getWatchesByFamily(@PathVariable("familyId") int familyId){
        return watchService.getWatchesByFamily(familyId);
    }
    */

    @GetMapping(path = "{familyId}/watches")
    public ResponseEntity<Page<WatchDTO>> getWatchesByFamilyPage(@PathVariable("familyId") int familyId,
                                                         @RequestParam Optional<Integer> page,
                                                         @RequestParam Optional<String> sortBy) {
        try{
            Page<WatchDTO> watchPage = watchService.getWatchesByFamilyPage(familyId, page.orElse(0), 20, sortBy.orElse("name"));
            return ResponseEntity.ok(watchPage);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
