package ch.nebulaWatches.nebulaWatchesAPI.watches.service;

import ch.nebulaWatches.nebulaWatchesAPI.watches.dto.WatchDTO;
import ch.nebulaWatches.nebulaWatchesAPI.watches.exceptions.WatchNotFoundException;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Brand;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Family;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WatchService {
    private final WatchRepository watchRepository;

    @Autowired
    public WatchService(WatchRepository watchRepository) {
        this.watchRepository = watchRepository;
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


    public List<Watch> getWatchesByFamily(int familyId){
        List<Watch> watches = watchRepository.findByFamily(familyId);
        return watches;
    }



    public byte[] getWatchImageBytes(String watchReference) throws WatchNotFoundException, SQLException {
        Optional<Watch> watch = watchRepository.findById(watchReference);

        if (watch.isPresent()) {
            return watch.get().getImageBytes();
        } else {
            throw new WatchNotFoundException("Watch not found with reference: " + watchReference);
        }
    }


    public Page<Watch> getWatchesByFamilyPage(int familyId, int page, int pageLength, String sortBy) {
        if (page < 0 || pageLength <= 0) {
            throw new IllegalArgumentException("Invalid page or length parameters");
        }
        Sort.Direction sortDirection = Sort.Direction.ASC;

        Pageable paging = PageRequest.of(page, pageLength, sortDirection, sortBy);
        return watchRepository.findByFamily(familyId, paging);
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
}
