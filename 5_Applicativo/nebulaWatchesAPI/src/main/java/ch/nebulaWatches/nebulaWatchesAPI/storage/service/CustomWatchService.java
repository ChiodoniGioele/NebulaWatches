package ch.nebulaWatches.nebulaWatchesAPI.storage.service;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.CustomWatch;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.CustomWatchRequest;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Favourite;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.StorageRequest;
import ch.nebulaWatches.nebulaWatchesAPI.storage.repository.CustomWatchRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.exceptions.WatchNotFoundException;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomWatchService {
    private final UserRepository userRepository;
    private final CustomWatchRepository customWatchRepository;
    public void addCustomWatch(CustomWatchRequest request) {
        CustomWatch customWatch = new CustomWatch();
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        customWatch.setUser(user);
        customWatch.setDescription(request.getDescription());
        customWatch.setReference(request.getReference());
        customWatch.setName(request.getName());
        customWatch.setRetailPrice(request.getRetailPrice());
        customWatch.setImage(request.getImage());

        customWatchRepository.save(customWatch);
    }

    public List<CustomWatch> getWatchesByUserId(int id) {
        return customWatchRepository.findByUser(id);
    }

    public byte[] getImageCustom(String watchReference) throws WatchNotFoundException, SQLException{
        Optional<CustomWatch> customWatch = customWatchRepository.findByReference(watchReference);

        if (customWatch.isPresent()) {
            return customWatch.get().getImage();
        } else {
            throw new WatchNotFoundException("Watch not found with reference: " + watchReference);
        }
    }
    public void removeCustomWatch(CustomWatchRequest request) {
        customWatchRepository.deleteByReference(request.getReference());
    }
    public Optional<CustomWatch> getWatch(String reference) {
        Optional<CustomWatch> watch = customWatchRepository.findByReference(reference);
        return watch;
    }
}
