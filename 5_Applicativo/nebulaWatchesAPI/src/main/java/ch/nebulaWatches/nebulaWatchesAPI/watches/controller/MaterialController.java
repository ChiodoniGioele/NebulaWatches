package ch.nebulaWatches.nebulaWatchesAPI.watches.controller;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Material;
import ch.nebulaWatches.nebulaWatchesAPI.watches.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/materials")
public class MaterialController {
    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public List<Material> getMaterials(){
        return materialService.getMaterials();
    }
}