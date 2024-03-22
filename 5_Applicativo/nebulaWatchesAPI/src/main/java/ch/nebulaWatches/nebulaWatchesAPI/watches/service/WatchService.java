package ch.nebulaWatches.nebulaWatchesAPI.watches.service;

import ch.nebulaWatches.nebulaWatchesAPI.watches.dto.WatchDTO;
import ch.nebulaWatches.nebulaWatchesAPI.watches.exceptions.WatchNotFoundException;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.*;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.FamilyRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WatchService {
    private final WatchRepository watchRepository;
    private final FamilyRepository familyRepository;

    @Autowired
    public WatchService(WatchRepository watchRepository, FamilyRepository familyRepository) {
        this.watchRepository = watchRepository;
        this.familyRepository = familyRepository;
    }

    public List<WatchDTO> getWatches(){
        List<Watch> watches = watchRepository.findAll();
        List<WatchDTO> watchDTOs = watches.stream()
                .map(Watch::toDTO)
                .collect(Collectors.toList());

        return watchDTOs;
    }

    public Optional<WatchDTO> getWatchDTO(String reference) {
        Optional<Watch> watch = watchRepository.findById(reference);
        return watch.map(Watch::toDTO);
    }

    public Optional<Watch> getWatch(String reference) {
        Optional<Watch> watch = watchRepository.findById(reference);
        return watch;
    }

    public List<Watch> getWatchesByBrand(String brandName){
        List<Watch> watches = watchRepository.findByBrand(brandName);
        return watches;
    }


    public List<WatchDTO> getWatchesByFamily(int familyId){
        List<Watch> watches = watchRepository.findByFamily(familyId);
        List<WatchDTO> watchesDTOs = watches.stream()
                .map(Watch::toDTO)
                .collect(Collectors.toList());
        return watchesDTOs;
    }



    public byte[] getWatchImageBytes(String watchReference) throws WatchNotFoundException, SQLException {
        Optional<Watch> watch = watchRepository.findById(watchReference);

        if (watch.isPresent()) {
            return watch.get().getImageBytes();
        } else {
            throw new WatchNotFoundException("Watch not found with reference: " + watchReference);
        }
    }


    public Page<WatchDTO> getWatchesByFamilyPage(int familyId, int page, int pageLength, String sortBy) {
        if (page < 0 || pageLength <= 0) {
            throw new IllegalArgumentException("Invalid page or length parameters");
        }

        Sort.Direction sortDirection = Sort.Direction.DESC;
        Pageable paging = PageRequest.of(page, pageLength, sortDirection, sortBy);

        // Fetch Page<Watch> directly from the repository
        Page<Watch> watchesPage = watchRepository.findByFamily(familyId, paging);

        // Convert Page<Watch> to Page<WatchDTO> using built-in map method
        Page<WatchDTO> watchesDTOPage = watchesPage.map(Watch::toDTO);

        return watchesDTOPage;
    }

    public Page<Watch> getWatchesBySearchQueryPage(String query, int page, int pageLength, String sortBy) {
        if (page < 0 || pageLength <= 0) {
            throw new IllegalArgumentException("Invalid page or length parameters");
        }
        Sort.Direction sortDirection = Sort.Direction.ASC;

        Pageable paging = PageRequest.of(page, pageLength, sortDirection, sortBy);

        return watchRepository.findBySearchQuery(query, paging);
    }


    public Page<Watch> getWatchesByBrandPages(String brandName, int page, int pageLength, String sortBy) {
        if (page < 0 || pageLength <= 0) {
            throw new IllegalArgumentException("Invalid page or length parameters");
        }
        Sort.Direction sortDirection = Sort.Direction.ASC;

        Pageable paging = PageRequest.of(page, pageLength, sortDirection, sortBy);

        return watchRepository.findByBrand(brandName, paging);
    }

    public Page<Watch> findByCriteria(String query, List<Brand> brands, List<Family> families,
                                        List<Material> materials, List<WatchShape> watchShapes, List<WatchIndexes> watchIndexes,
                                      List<DialColor> dialColors, List<DialFinish> dialFinishes, int page, int size, String sortBy) {
        // Implement logic to fetch filtered watches based on the provided criteria
        Sort.Direction sortDirection = Sort.Direction.ASC;
        Pageable paging = PageRequest.of(page, size, sortDirection, sortBy);

        List<String> materialNames = materials.stream()
                .map(Material::getName)
                .collect(Collectors.toList());

        List<Long> familyIds = families.stream()
                .map(Family::getId)
                .collect(Collectors.toList());

        List<String> brandNames = brands.stream()
                .map(Brand::getName)
                .collect(Collectors.toList());

        //

        List<String> watchShapesNames = watchShapes.stream()
                .map(WatchShape::getName)
                .collect(Collectors.toList());

        List<String> watchIndexesNames = watchIndexes.stream()
                .map(WatchIndexes::getName)
                .collect(Collectors.toList());

        List<String> dialColorsNames = dialColors.stream()
                .map(DialColor::getName)
                .collect(Collectors.toList());

        List<String> dialFinishesNames = dialFinishes.stream()
                .map(DialFinish::getName)
                .collect(Collectors.toList());

        if(query.isEmpty()){
            query = null;
        }
        if(brandNames.size() == 0){
            brandNames = null;
        }
        if(familyIds.size() == 0){
            familyIds = null;
        }
        if(materialNames.size() == 0){
            materialNames = null;
        }

        if(watchShapesNames.size() == 0){
            watchShapesNames = null;
        }
        if(watchIndexesNames.size() == 0){
            watchIndexesNames = null;
        }
        if(dialColorsNames.size() == 0){
            dialColorsNames = null;
        }
        if(dialFinishesNames.size() == 0){
            dialFinishesNames = null;
        }


        //return watchRepository.findByCriteria(query, brands, families, materialNames, paging);
        return watchRepository.findByCriteria(query, brandNames, familyIds, materialNames, watchShapesNames, watchIndexesNames, dialColorsNames, dialFinishesNames, paging);
    }
}
