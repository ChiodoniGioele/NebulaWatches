package ch.nebulaWatches.nebulaWatchesAPI.watches.service;

import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Hands;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.HandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandsService {
    private final HandsRepository handsRepository;

    @Autowired
    public HandsService(HandsRepository handsRepository) {
        this.handsRepository = handsRepository;
    }

    public List<Hands> getHands(){
        return handsRepository.findAll();
    }
}
