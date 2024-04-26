package ch.nebulaWatches.nebulaWatchesAPI.watches.service;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Brand;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Family;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyService {
    private final FamilyRepository familyRepository;

    @Autowired
    public FamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    public List<Family> getFamilies(){
        return familyRepository.findAll();
    }

    public List<Family> getFamiliesByBrand(String brandName){
        return familyRepository.findByBrand(brandName);
    }

    public Page<Family> getFamiliesByBrandPage(String brandName, int page, int pageLength, String sortBy) throws IllegalArgumentException {
        if (page < 0 || pageLength <= 0) {
            throw new IllegalArgumentException("Invalid page or length parameters");
        }
        Sort.Direction sortDirection = Sort.Direction.ASC;

        Pageable paging = PageRequest.of(page, pageLength, sortDirection, sortBy);
        return familyRepository.findByBrand(brandName, paging);
    }
}
