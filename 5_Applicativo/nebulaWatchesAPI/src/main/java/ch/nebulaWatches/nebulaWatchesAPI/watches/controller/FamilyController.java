package ch.nebulaWatches.nebulaWatchesAPI.watches.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Family;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/families")
public class FamilyController {
    private final FamilyService familyService;

    @Autowired
    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @GetMapping
    public List<Family> getFamilies(){
        return familyService.getFamilies();
    }
}
