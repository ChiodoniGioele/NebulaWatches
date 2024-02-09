package ch.nebulaWatches.nebulaWatchesAPI.watches.service;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Brand;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Family;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.BrandRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getBrands(){
        return brandRepository.findAll();
    }
}
