package ch.nebulaWatches.nebulaWatchesAPI.watches.service;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.DialFinish;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.DialFinishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DialFinishService {
    private final DialFinishRepository dialFinishRepository;

    @Autowired
    public DialFinishService(DialFinishRepository dialFinishRepository) {
        this.dialFinishRepository = dialFinishRepository;
    }

    public List<DialFinish> getDialFinishes(){
        return dialFinishRepository.findAll();
    }
}
