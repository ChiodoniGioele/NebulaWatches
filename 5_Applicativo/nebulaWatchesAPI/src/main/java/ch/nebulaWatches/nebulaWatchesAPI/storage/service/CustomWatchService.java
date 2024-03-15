package ch.nebulaWatches.nebulaWatchesAPI.storage.service;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.CustomWatch;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.CustomWatchRequest;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Favourite;
import ch.nebulaWatches.nebulaWatchesAPI.storage.repository.CustomWatchRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomWatchService {
    private final UserRepository userRepository;
    private final CustomWatchRepository customWatchRepository;
    public void addCustomWatch(CustomWatchRequest request) {
        CustomWatch customWatch = new CustomWatch();
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        customWatch.setUser(user);
        customWatch.setDescription(request.getDescription());
        customWatch.setImage(request.getImage());
        customWatch.setReference(request.getReference());
        customWatch.setName(request.getName());
        customWatch.setRetailPrice(request.getRetailPrice());

        customWatchRepository.save(customWatch);
    }

    public List<CustomWatch> getWatchesByUserId(int id) {
        return customWatchRepository.findByUser(id);
    }

}
