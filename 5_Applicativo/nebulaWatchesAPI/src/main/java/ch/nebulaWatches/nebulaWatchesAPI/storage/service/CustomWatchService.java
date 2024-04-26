package ch.nebulaWatches.nebulaWatchesAPI.storage.service;

import ch.nebulaWatches.nebulaWatchesAPI.utils.InputUtils;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import ch.nebulaWatches.nebulaWatchesAPI.storage.exceptions.DuplicateReferenceException;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.CustomWatch;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.CustomWatchRequest;
import ch.nebulaWatches.nebulaWatchesAPI.storage.repository.CustomWatchRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.exceptions.WatchNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomWatchService {
    private final UserRepository userRepository;
    private final CustomWatchRepository customWatchRepository;
    public void addCustomWatch(CustomWatchRequest request) throws IOException {
        CustomWatch customWatch = new CustomWatch();
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        customWatch.setUser(user);
        customWatch.setDescription(InputUtils.testInput(request.getDescription()));
        customWatch.setReference(InputUtils.testInput(request.getReference()));
        customWatch.setName(InputUtils.testInput(request.getName()));
        if(request.getRetailPrice() >= 0){
            customWatch.setRetailPrice(request.getRetailPrice());
        }else{
            customWatch.setRetailPrice(0f);
        }

        customWatch.setImage(setImage(request.getImage()));
        if(canUseRef(request.getReference())){
            customWatchRepository.save(customWatch);
        }else{
            throw new DuplicateReferenceException("Reference already exists. Please use another");
        }

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
        //customWatchRepository.deleteByReference(request.getReference());
        Optional<CustomWatch> cw = customWatchRepository.findByReference(request.getReference());
        if(cw.isPresent()){
            CustomWatch customWatch = cw.get();
            customWatch.setStatus(false);
            customWatchRepository.save(customWatch);
        }
    }
    public Optional<CustomWatch> getWatch(String reference) {
        return customWatchRepository.findByReference(reference);
    }

    public byte[] setImage(MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            if (!InputUtils.isValidImageType(imageFile)) {
                throw new IllegalArgumentException("Invalid file type. Please upload an image file.");
            }
            return imageFile.getBytes();
        } else {
            throw new IllegalArgumentException("Image file is required.");
        }
    }
    public boolean canUseRef(String ref){
        Optional<CustomWatch> customWatch= customWatchRepository.findByReference(ref);

        return customWatch.isEmpty();
    }
}
