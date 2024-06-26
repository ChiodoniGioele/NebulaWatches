package ch.nebulaWatches.nebulaWatchesAPI.storage.service;

import ch.nebulaWatches.nebulaWatchesAPI.clients.model.Client;
import ch.nebulaWatches.nebulaWatchesAPI.clients.repository.ClientRepository;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.UserService;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.*;
import ch.nebulaWatches.nebulaWatchesAPI.storage.repository.CustomWatchRepository;
import ch.nebulaWatches.nebulaWatchesAPI.storage.repository.StorageRepository;
import ch.nebulaWatches.nebulaWatchesAPI.team.model.Team;
import ch.nebulaWatches.nebulaWatchesAPI.team.repository.TeamRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import ch.nebulaWatches.nebulaWatchesAPI.watches.repository.WatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.DoubleToIntFunction;

import static ch.nebulaWatches.nebulaWatchesAPI.team.service.TeamService.getEndOfMonth;
import static ch.nebulaWatches.nebulaWatchesAPI.team.service.TeamService.getStartOfMonth;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
    private final UserRepository userRepository;
    private final WatchRepository watchRepository;
    private final CustomWatchRepository customWatchRepository;
    private final ClientRepository clientRepository;
    private final TeamRepository teamRepository;
    private final UserService userService;

    public List<Storage> getAllStorage() {
        return storageRepository.findAll();
    }

    public int getQuantityStorage(Long id) {
        return storageRepository.findQuantityById(id);
    }

    public Optional<Storage> getStorage(int id) {
        return storageRepository.findById(id);
    }

    public List<Storage> getWatchesByUserId(int userId) {
        return storageRepository.findByUser(userId);
    }

    public Page<Storage> getWatchesByUserId(int userId, int page, int pageLength, String sortBy) {
        if (page < 0 || pageLength <= 0) {
            throw new IllegalArgumentException("Invalid page or length parameters");
        }
        Sort.Direction sortDirection = Sort.Direction.ASC;
        return storageRepository.findByUser(userId, PageRequest.of(page, pageLength, sortDirection, sortBy));

    }

    public void addWatchToStorage(StorageRequest request) {
        if (!isRequestInStorage(request)) {
            Storage storage = new Storage();
            User user = userRepository.findByEmail(request.getUser_email())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            storage.setUser(user);
            if (!request.getWatch_reference().isEmpty()) {
                Watch watch = watchRepository.findByReference(request.getWatch_reference())
                        .orElseThrow(() -> new IllegalArgumentException("Watch not found"));
                storage.setWatch(watch);
            } else if (!request.getCustom_watch_reference().isEmpty()) {
                CustomWatch customWatch = customWatchRepository.findByReference(request.getCustom_watch_reference())
                        .orElseThrow(() -> new IllegalArgumentException("Custom Watch not found"));
                storage.setCustomWatch(customWatch);
            }

            if (request.getQuantity() <= 0 || request.getQuantity() > 2147483646) {
                storage.setQuantity(1);
            } else {
                storage.setQuantity(request.getQuantity());
            }
            storage.setStatus(new StatusStorage(request.getStatus()));
            if (request.getBuyPrice() < 0) {
                storage.setBuyPrice(0);
            } else {
                storage.setBuyPrice(request.getBuyPrice());
            }
            storage.setPurchaseDate(request.getPurchaseDate());
            if (storage.getStatus().getName().equals("Sold")) {
                if (request.getSellPrice() < 0) {
                    storage.setSellPrice(0);
                } else {
                    storage.setSellPrice(request.getSellPrice());
                }
                Client client = clientRepository.findById(request.getClientId())
                        .orElseThrow(() -> new IllegalArgumentException("Client not found"));
                storage.setClient(client);
                Team team = teamRepository.findById(request.getTeamId())
                        .orElseThrow(() -> new IllegalArgumentException("Team not found"));
                storage.setTeam(team);
                storage.setSellDate(request.getSellDate());
            } else {
                storage.setSellPrice(0);
            }

            storageRepository.save(storage);
        }

    }

    public void removeFromStorage(StorageRequest request) {
        storageRepository.deleteById(request.getId());
    }

    public void editStorage(StorageRequest request) throws IllegalArgumentException {
        Optional<Storage> storage = getStorage((int) request.getId());
        Storage newStorage = new Storage();
        if (storage.isPresent()) {
            Storage storage1 = storage.get();
            if (request.getQuantity() > 0 && storage1.getQuantity() >= request.getQuantity()) {
                newStorage.setQuantity(request.getQuantity());
                newStorage.setUser(storage1.getUser());
                newStorage.setStatus(new StatusStorage(request.getStatus()));
                if (storage1.getWatch() != null) {
                    newStorage.setWatch(storage1.getWatch());
                } else if (storage1.getCustomWatch() != null) {
                    newStorage.setCustomWatch(storage1.getCustomWatch());
                }
                newStorage.setSellPrice(request.getSellPrice());
                newStorage.setBuyPrice(request.getBuyPrice());
                Client client = clientRepository.findById(request.getClientId())
                        .orElseThrow(() -> new IllegalArgumentException("Client not found"));
                newStorage.setClient(client);
                Team team = teamRepository.findById(request.getTeamId())
                        .orElseThrow(() -> new IllegalArgumentException("Team not found"));
                newStorage.setTeam(team);

                newStorage.setPurchaseDate(request.getPurchaseDate());
                newStorage.setSellDate(request.getSellDate());

                if (!isRequestInStorageEdit(request)) {
                    storageRepository.save(newStorage);
                }

                if (storage1.getQuantity() == request.getQuantity()) {
                    storageRepository.deleteById(request.getId());
                } else {
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


    public List<Storage> getStorageByTeamId(Long id) {
        return storageRepository.getByTeamId(id);
    }

    public boolean isRequestInStorage(StorageRequest request) {

        User user = userRepository.findByEmail(request.getUser_email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Optional<Storage> existingStorage;
        if (request.getStatus().equals("Owned")) {
            if (!Objects.equals(request.getWatch_reference(), "")) {
                existingStorage = storageRepository.getByRequest(request.getWatch_reference(), user.getId(), request.getPurchaseDate(), request.getBuyPrice(), request.getStatus());
            } else {
                existingStorage = storageRepository.getByRequest(request.getCustom_watch_reference(), user.getId(), request.getPurchaseDate(), request.getBuyPrice(), request.getStatus());
            }
            if (existingStorage.isPresent()) {
                Storage storage = existingStorage.get();
                storage.setQuantity(storage.getQuantity() + request.getQuantity());
                storageRepository.save(storage);
                return true;
            } else {
                return false;
            }
        } else {
            if (!Objects.equals(request.getWatch_reference(), "")) {
                existingStorage = storageRepository.getByRequestSold(request.getWatch_reference(), user.getId(), request.getPurchaseDate(), request.getBuyPrice(), request.getStatus(), request.getSellPrice(), request.getSellDate(), request.getTeamId(), request.getClientId());
            } else {
                existingStorage = storageRepository.getByRequestSold(request.getCustom_watch_reference(), user.getId(), request.getPurchaseDate(), request.getBuyPrice(), request.getStatus(), request.getSellPrice(), request.getSellDate(), request.getTeamId(), request.getClientId());
            }
            if (existingStorage.isPresent()) {
                Storage storage = existingStorage.get();
                storage.setQuantity(storage.getQuantity() + request.getQuantity());
                storageRepository.save(storage);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean isRequestInStorageEdit(StorageRequest request) {
        User user = userRepository.findByEmail(request.getUser_email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Optional<Storage> existingStorage;

        if (!Objects.equals(request.getWatch_reference(), "")) {
            existingStorage = storageRepository.getByRequestSold(request.getWatch_reference(), user.getId(), request.getPurchaseDate(), request.getBuyPrice(), "Sold", request.getSellPrice(), request.getSellDate(), request.getTeamId(), request.getClientId());
        } else {
            existingStorage = storageRepository.getByRequestSold(request.getCustom_watch_reference(), user.getId(), request.getPurchaseDate(), request.getBuyPrice(), "Sold", request.getSellPrice(), request.getSellDate(), request.getTeamId(), request.getClientId());
        }
        if (existingStorage.isPresent()) {
            Storage storage = existingStorage.get();
            storage.setQuantity(storage.getQuantity() + request.getQuantity());
            storageRepository.save(storage);

            return true;
        } else {
            return false;
        }
    }

    public List<Storage> getWatchesOwnedByClientAndUser(BuysClientRequest request){
        return storageRepository.findByUserEmailAndClientIdAndStatus(request.getUserEmail(), request.getClientId(), "Sold");
    }

    public int getWatchesOwnedByClientMonth(Long id, int month){
        LocalDate l1 = getStartOfMonth(month);
        LocalDate l2 = getEndOfMonth(month);
        Optional<Integer> opt = storageRepository.sumQuantityByClientMonth(id, l1, l2, "Sold");
        return opt.orElse(0);
    }

    public static LocalDate getStartOfMonth(int months) {
        return LocalDate.now().minusMonths(months).with(TemporalAdjusters.firstDayOfMonth());
    }
    public static LocalDate getEndOfMonth(int months) {
        return LocalDate.now().minusMonths(months).with(TemporalAdjusters.lastDayOfMonth());

    }

}
