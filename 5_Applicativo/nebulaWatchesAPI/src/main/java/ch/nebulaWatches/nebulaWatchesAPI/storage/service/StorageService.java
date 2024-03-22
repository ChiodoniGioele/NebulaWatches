package ch.nebulaWatches.nebulaWatchesAPI.storage.service;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.UserService;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.*;
import ch.nebulaWatches.nebulaWatchesAPI.storage.repository.StorageRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.WatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
    private final UserRepository userRepository;
    private final WatchRepository watchRepository;
    private final UserService userService;

    public List<Storage> getAllStorage() {
        return storageRepository.findAll();
    }

    public List<Storage> getWatchesByUserId(int userId) {
        return storageRepository.findByUser(userId);
    }
    public void addWatchToStorage(StorageRequest request) {
        Storage storage = new Storage();
        User user = userRepository.findByEmail(request.getUser_email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        storage.setUser(user);
        Watch watch = watchRepository.findByReference(request.getWatch_reference())
                .orElseThrow(() -> new IllegalArgumentException("Watch not found"));

        storage.setWatch(watch);
        if(request.getQuantity() <= 0 || request.getQuantity() > 100){
            storage.setQuantity(1);
        }else {
            storage.setQuantity(request.getQuantity());
        }
        storage.setStatus(new StatusStorage(request.getStatus()));

        storageRepository.save(storage);
    }

    public void removeFromStorage(StorageRequest request) {
        storageRepository.deleteById(request.getId());
    }


}
