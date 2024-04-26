package ch.nebulaWatches.nebulaWatchesAPI.watches.service;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Brand;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.WatchIndexes;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.WatchShape;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.BrandRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.WatchIndexesRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.WatchShapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchIndexesService {
    private final WatchIndexesRepository watchIndexesRepository;

    @Autowired
    public WatchIndexesService(WatchIndexesRepository watchIndexesRepository) {
        this.watchIndexesRepository = watchIndexesRepository;
    }

    public List<WatchIndexes> getWatchIndexes(){
        return watchIndexesRepository.findAll();
    }
}
