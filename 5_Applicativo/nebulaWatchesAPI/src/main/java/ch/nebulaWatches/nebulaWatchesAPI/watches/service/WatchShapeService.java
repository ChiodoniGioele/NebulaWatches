package ch.nebulaWatches.nebulaWatchesAPI.watches.service;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Brand;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.WatchShape;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.BrandRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.WatchShapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchShapeService {
    private final WatchShapeRepository watchShapeRepository;

    @Autowired
    public WatchShapeService(WatchShapeRepository watchShapeRepository) {
        this.watchShapeRepository = watchShapeRepository;
    }

    public List<WatchShape> getWatchShapes(){
        return watchShapeRepository.findAll();
    }
}
