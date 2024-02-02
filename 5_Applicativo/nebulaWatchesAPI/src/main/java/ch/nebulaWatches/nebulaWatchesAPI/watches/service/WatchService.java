package ch.nebulaWatches.nebulaWatchesAPI.watches.service;

import ch.nebulaWatches.nebulaWatchesAPI.watches.dto.WatchDTO;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

}
