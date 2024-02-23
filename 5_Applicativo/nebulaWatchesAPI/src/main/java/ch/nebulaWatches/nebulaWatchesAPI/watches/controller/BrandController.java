package ch.nebulaWatches.nebulaWatchesAPI.watches.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.exceptions.WatchNotFoundException;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Brand;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Family;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.BrandService;
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
@RequestMapping(path = "/v1/brands")
public class BrandController {
    private final BrandService brandService;
    private final WatchService watchService;
    private final FamilyService familyService;
    private Random rand;

    @Autowired
    public BrandController(BrandService brandService, WatchService watchService, FamilyService familyService) {
        this.brandService = brandService;
        this.watchService = watchService;
        this.familyService = familyService;
        rand = new Random();
    }

    @GetMapping
    public List<Brand> getBrands(){
        return brandService.getBrands();
    }

    @GetMapping(path = "{brandName}/rndimage")
    public ResponseEntity<byte[]> getBrandRandomImage(@PathVariable("brandName") String brandName) {
        try {
            List<Watch> watches = watchService.getWatchesByBrand(brandName);
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

    @GetMapping(path = "{brandName}/families")
    public List<Family> getFamilies(@PathVariable("brandName") String brandName){
        List<Family> families = familyService.getFamiliesByBrand(brandName);
        return families;
    }

}