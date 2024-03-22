package ch.nebulaWatches.nebulaWatchesAPI.watches.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.dto.WatchDTO;
import ch.nebulaWatches.nebulaWatchesAPI.watches.exceptions.WatchNotFoundException;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.*;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.*;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/v1/watches")
public class WatchController {
    private final WatchService watchService;
    private final BrandRepository brandRepository;
    private final FamilyRepository familyRepository;
    private final WatchShapeRepository watchShapeRepository;
    private final WatchIndexesRepository watchIndexesRepository;
    private final DialColorRepository dialColorRepository;
    private final DialFinishRepository dialFinishRepository;
    private final MaterialRepository materialRepository;

    @Autowired
    public WatchController(WatchService watchService, BrandRepository brandRepository, FamilyRepository familyRepository,
                           WatchShapeRepository watchShapeRepository, WatchIndexesRepository watchIndexesRepository,
                           DialColorRepository dialColorRepository, DialFinishRepository dialFinishRepository,
                           MaterialRepository materialRepository) {
        this.watchService = watchService;
        this.brandRepository = brandRepository;
        this.familyRepository = familyRepository;
        this.watchShapeRepository = watchShapeRepository;
        this.watchIndexesRepository = watchIndexesRepository;
        this.dialColorRepository = dialColorRepository;
        this.dialFinishRepository = dialFinishRepository;
        this.materialRepository = materialRepository;
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
    public ResponseEntity<byte[]> getWatchImage(@PathVariable("watchReference") String watchReference) {
        try {
            byte[] imageBytes = watchService.getWatchImageBytes(watchReference);
            return ResponseEntity.ok(imageBytes);
        } catch (WatchNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping(path = "search")
    public ResponseEntity<Page<Watch>> getWatchesBySearchQueryPage(@RequestParam Optional<String> query,
                                                              @RequestParam Optional<Integer> page,
                                                              @RequestParam Optional<String> sortBy) {
        try{
            Page<Watch> watchPage = watchService.getWatchesBySearchQueryPage(query.orElse(""), page.orElse(0), 20, sortBy.orElse("name"));
            return ResponseEntity.ok(watchPage);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/filter")
    public ResponseEntity<Page<Watch>> findByCriteria(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) List<Brand> brands,
            @RequestParam(required = false) List<Family> families,
            @RequestParam(required = false) List<WatchShape> watchShapes,
            @RequestParam(required = false) List<WatchIndexes> watchIndexes,
            @RequestParam(required = false) List<DialColor> dialColors,
            @RequestParam(required = false) List<DialFinish> dialFinishes,
            @RequestParam(required = false) List<Material> materials) {
        // Convert list of string IDs to list of objects (e.g., Brand, Family, Material)
        /*
        List<Brand> brandObjects = null;

        if (brands != null && !brands.isEmpty()) {
            for(int i = 0; i < brands.size(); i++){
                Optional<Brand> b = brandRepository.findById(brands.get(i));
                brandObjects.add(b.orElse(null));
            }
        }

        List<Family> familyObjects = null;
        if (families != null && !families.isEmpty()) {
            for(int i = 0; i < families.size(); i++){
                Optional<Family> f = familyRepository.findById(families.get(i));
                familyObjects.add(f.orElse(null));
            }
        }

        /*
        List<Material> materialObjects = null;
        if (materials != null && !materials.isEmpty()) {
            for(int i = 0; i < materials.size(); i++){
                Optional<Material> m = materialRepository.findById(materials.get(i));
                materialObjects.add(m.orElse(null));
            }
        }*/

        Page<Watch> watches = watchService.findByCriteria(query, brands, families, materials, watchShapes, watchIndexes, dialColors, dialFinishes, page, pageSize, sortBy);
        return ResponseEntity.ok(watches);
    }



}
