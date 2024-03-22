package ch.nebulaWatches.nebulaWatchesAPI.storage.service;

import ch.nebulaWatches.nebulaWatchesAPI.clients.model.Client;
import ch.nebulaWatches.nebulaWatchesAPI.clients.repository.ClientRepository;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.UserService;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.*;
import ch.nebulaWatches.nebulaWatchesAPI.storage.repository.CustomWatchRepository;
import ch.nebulaWatches.nebulaWatchesAPI.storage.repository.StorageRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.WatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
    private final UserRepository userRepository;
    private final WatchRepository watchRepository;
    private final CustomWatchRepository customWatchRepository;
    private final ClientRepository clientRepository;
    private final UserService userService;

    public List<Storage> getAllStorage() {
        return storageRepository.findAll();
    }
    public int getQuantityStorage(Long id){ return storageRepository.findQuantityById(id);}
    public Optional<Storage> getStorage(int id){ return storageRepository.findById(id);}

    public List<Storage> getWatchesByUserId(int userId) {
        return storageRepository.findByUser(userId);
    }
    public void addWatchToStorage(StorageRequest request) {
        Storage storage = new Storage();
        User user = userRepository.findByEmail(request.getUser_email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        storage.setUser(user);
        if (!request.getWatch_reference().isEmpty()) {
            Watch watch = watchRepository.findByReference(request.getWatch_reference())
                    .orElseThrow(() -> new IllegalArgumentException("Watch not found"));
            storage.setWatch(watch);
        }else if(!request.getCustom_watch_reference().isEmpty()){
            CustomWatch customWatch = customWatchRepository.findByReference(request.getCustom_watch_reference())
                    .orElseThrow(() -> new IllegalArgumentException("Custom Watch not found"));
            storage.setCustomWatch(customWatch);
        }

        if(request.getQuantity() <= 0 || request.getQuantity() > 100){
            storage.setQuantity(1);
        }else {
            storage.setQuantity(request.getQuantity());
        }
        storage.setStatus(new StatusStorage(request.getStatus()));
        if(request.getBuyPrice() < 0 ){
            storage.setBuyPrice(0);
        }else {
            storage.setBuyPrice(request.getBuyPrice());
        }
        if(storage.getStatus().getName().equals("Sold")){
            if(request.getSellPrice() < 0 ){
                storage.setSellPrice(0);
            }else {
                storage.setSellPrice(request.getSellPrice());
            }
        }else{
            storage.setSellPrice(0);
        }

        storageRepository.save(storage);
    }

    public void removeFromStorage(StorageRequest request) {
        storageRepository.deleteById(request.getId());
    }

    public void editStorage(StorageRequest request) throws IllegalArgumentException {
        Optional<Storage> storage = getStorage((int)request.getId());
        Storage newStorage = new Storage();
        if (storage.isPresent()) {
            Storage storage1 = storage.get();
            if (request.getQuantity() > 0 && storage1.getQuantity() >= request.getQuantity()) {
                newStorage.setQuantity(request.getQuantity());
                newStorage.setUser(storage1.getUser());
                newStorage.setStatus(new StatusStorage(request.getStatus()));
                if(storage1.getWatch() != null){
                    newStorage.setWatch(storage1.getWatch());
                }else if(storage1.getCustomWatch() != null){
                    newStorage.setCustomWatch(storage1.getCustomWatch());
                }
                newStorage.setSellPrice(request.getSellPrice());
                newStorage.setBuyPrice(request.getBuyPrice());
                Client client = clientRepository.findById(request.getClientId())
                        .orElseThrow(() -> new IllegalArgumentException("Client not found"));
                newStorage.setClient(client);

                storageRepository.save(newStorage);

                if(storage1.getQuantity() == request.getQuantity()){
                    storageRepository.deleteById(request.getId());
                }else{
                    int qty = storage1.getQuantity() - request.getQuantity();
                    storageRepository.updateQuantityById(qty, request.getId());
                }
            } else {
                throw new IllegalArgumentException("Invalid quantity");
            }
        } else {
            throw new IllegalArgumentException("Storage not found");
        }
    }


}
