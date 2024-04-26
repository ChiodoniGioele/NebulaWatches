package ch.nebulaWatches.nebulaWatchesAPI.watches.service;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Brand;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Family;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.BrandRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        //return brandRepository.findAll();
        return brandRepository.findAllNotWatchEmptyBrands();
    }

    public Page<Brand> getBrandsPage(int page, int pageLength, String sortBy) {
        if (page < 0 || pageLength <= 0) {
            throw new IllegalArgumentException("Invalid page or length parameters");
        }
        Sort.Direction sortDirection = Sort.Direction.ASC;
        //return brandRepository.findAll(PageRequest.of(page, pageLength, sortDirection, sortBy));
        return brandRepository.findAllNotWatchEmptyBrands(PageRequest.of(page, pageLength, sortDirection, sortBy));
    }
}
