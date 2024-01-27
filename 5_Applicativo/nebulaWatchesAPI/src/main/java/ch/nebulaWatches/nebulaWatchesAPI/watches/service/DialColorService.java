package ch.nebulaWatches.nebulaWatchesAPI.watches.service;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Brand;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.DialColor;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.BrandRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.DialColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DialColorService {
    private final DialColorRepository dialColorRepository;

    @Autowired
    public DialColorService(DialColorRepository dialColorRepository) {
        this.dialColorRepository = dialColorRepository;
    }

    public List<DialColor> getDialColors(){
        return dialColorRepository.findAll();
    }
}
