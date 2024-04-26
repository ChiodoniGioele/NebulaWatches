package ch.nebulaWatches.nebulaWatchesAPI.storage.service;

import ch.nebulaWatches.nebulaWatchesAPI.storage.model.StatusStorage;
import ch.nebulaWatches.nebulaWatchesAPI.storage.repository.StatusStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusStorageService {

    private final StatusStorageRepository statusStorageRepository;

    @Autowired
    public StatusStorageService(StatusStorageRepository statusStorageRepository) {
        this.statusStorageRepository = statusStorageRepository;
    }

    public void insertDafault() {
        if (statusStorageRepository.findByName("Owned") == null) {
            StatusStorage owned = new StatusStorage("Owned");
            statusStorageRepository.save(owned);
        }
        if (statusStorageRepository.findByName("Sold") == null) {
            StatusStorage sold = new StatusStorage("Sold");
            statusStorageRepository.save(sold);
        }
        if (statusStorageRepository.findByName("Shipped") == null) {
            StatusStorage shipped = new StatusStorage("Shipped");
            statusStorageRepository.save(shipped);
        }
    }
}