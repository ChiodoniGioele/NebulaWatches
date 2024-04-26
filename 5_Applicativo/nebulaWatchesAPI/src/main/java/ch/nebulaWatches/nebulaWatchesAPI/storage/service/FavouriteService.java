package ch.nebulaWatches.nebulaWatchesAPI.storage.service;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.*;
import ch.nebulaWatches.nebulaWatchesAPI.storage.repository.FavouriteRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.WatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;
    private final WatchRepository watchRepository;

    public List<Favourite> getAllFavourite() {
        return favouriteRepository.findAll();
    }

    public List<Watch> getWatchesByUserId(int userId) {
        return favouriteRepository.findByUser(userId);
    }
    public void addWatchToFavourites(FavouriteRequest request) {
        Favourite favourite = new Favourite();
        User user = userRepository.findByEmail(request.getUser_email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        favourite.setUser(user);
        Watch watch = watchRepository.findByReference(request.getWatch_reference())
                .orElseThrow(() -> new IllegalArgumentException("Watch not found"));
        favourite.setWatch(watch);

        favouriteRepository.save(favourite);
    }

    public void removeFromFavourites(FavouriteRequest request) {
        User user = userRepository.findByEmail(request.getUser_email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Watch watch = watchRepository.findByReference(request.getWatch_reference())
                .orElseThrow(() -> new IllegalArgumentException("Watch not found"));

        favouriteRepository.deleteByUserAndWatch(user, watch);
    }

    public boolean isWatchInFavorites(String watchReference, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Watch watch = watchRepository.findByReference(watchReference)
                .orElseThrow(() -> new IllegalArgumentException("Watch not found"));
        return favouriteRepository.existsByUserAndWatch(user, watch);
    }

}

