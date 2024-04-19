package ch.nebulaWatches.nebulaWatchesAPI.storage.controller;

import ch.nebulaWatches.nebulaWatchesAPI.security.service.UserService;
import ch.nebulaWatches.nebulaWatchesAPI.storage.exceptions.DuplicateReferenceException;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.*;
import ch.nebulaWatches.nebulaWatchesAPI.storage.service.CustomWatchService;
import ch.nebulaWatches.nebulaWatchesAPI.storage.service.StatusStorageService;
import ch.nebulaWatches.nebulaWatchesAPI.storage.service.StorageService;
import ch.nebulaWatches.nebulaWatchesAPI.watches.exceptions.WatchNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@CrossOrigin
@RestController
@RequestMapping("/v1/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;
    private final UserService userService;
    private final StatusStorageService statusStorageService;
    private final CustomWatchService customWatchService;

    @GetMapping("/{userEmail}")
    public ResponseEntity<List<Storage>> getWatchesByStorageAndUser(@PathVariable String userEmail) {
        int userId = userService.getId(userEmail);
        List<Storage> storages = storageService.getWatchesByUserId(userId);
        return ResponseEntity.ok(storages);
    }

    @PostMapping("/addWatchToStorage")
    public ResponseEntity<String> addWatchToStorage(@RequestBody StorageRequest request) {
        try {
            statusStorageService.insertDafault();
            storageService.addWatchToStorage(request);
            return ResponseEntity.ok("Watch added to storage successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add watch to storage: " + e.getMessage());
        }
    }
    @PostMapping("/removeStorageWatch")
    public ResponseEntity<String> removeStorageWatch(@RequestBody StorageRequest request) {
        try {
            storageService.removeFromStorage(request);
            return ResponseEntity.ok("Watch removed from storage successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to remove watch from storage: " + e.getMessage());
        }
    }

    @PostMapping("/editStorageWatch")
    public ResponseEntity<String> editStorageWatch(@RequestBody StorageRequest request) {
        try {
            storageService.editStorage(request);
            return ResponseEntity.ok("Watch edited successfully.");
        } catch (IllegalArgumentException ill){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Quantity must be between 1 and number in your storage: " + ill.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to edit watch in storage: " + e.getMessage());
        }
    }

    @PostMapping("/saveCustom")
    public ResponseEntity<String> addCustomWatch(@RequestParam("reference") String reference,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("retailPrice") float retailPrice,
                                                 @RequestParam("email") String email,
                                                 @RequestParam("file") MultipartFile file)
    {
        try {
            CustomWatchRequest watchRequest = new CustomWatchRequest();
            watchRequest.setReference(reference);
            watchRequest.setEmail(email);
            watchRequest.setName(name);
            watchRequest.setDescription(description);
            watchRequest.setRetailPrice(retailPrice);
            watchRequest.setImage(file);

            customWatchService.addCustomWatch(watchRequest);
            return ResponseEntity.ok("Watch added to storage successfully.");
        } catch (IOException io){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Invalid file passed: " + io.getMessage());
        }catch (DuplicateReferenceException De){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(De.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add watch to storage: " + e.getMessage());
        }
    }

    @GetMapping("/custom/{userEmail}")
    public ResponseEntity<List<CustomWatch>> getCustomWatches(@PathVariable String userEmail) {
        int userId = userService.getId(userEmail);
        List<CustomWatch> watches = customWatchService.getWatchesByUserId(userId);
        return ResponseEntity.ok(watches);
    }

    @GetMapping("/custom/{reference}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable String reference) {
        try {
            byte[] imageBytes = customWatchService.getImageCustom(reference);
            return ResponseEntity.ok(imageBytes);
        } catch (WatchNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/removeCustom")
    public ResponseEntity<String> removeCustomWatch(@RequestBody CustomWatchRequest request) {
        try {
            customWatchService.removeCustomWatch(request);
            return ResponseEntity.ok("Watch removed from storage successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to remove watch from storage: " + e.getMessage());
        }
    }
    @GetMapping("/getCustomInfo/{watchReference}")
    public Optional<CustomWatch> getCustomWatch(@PathVariable String watchReference){
        return customWatchService.getWatch(watchReference);
    }

    @GetMapping("/getWatchSoldByClient")
    public ResponseEntity<List<Storage>> getSoldWatchesByClient(@RequestParam String userEmail, @RequestParam Long clientId) {
        try {
            BuysClientRequest request = new BuysClientRequest();
            request.setUserEmail(userEmail);
            request.setClientId(clientId);

            List<Storage> watches = storageService.getWatchesOwnedByClientAndUser(request);
            return ResponseEntity.ok(watches);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getWatchSoldByClientNum")
    public ResponseEntity<Integer> getSoldWatchesByClientNum(@RequestParam String userEmail, @RequestParam Long clientId) {
        try {
            BuysClientRequest request = new BuysClientRequest();
            request.setUserEmail(userEmail);
            request.setClientId(clientId);

            System.err.println(userEmail + "  " + clientId);

            List<Storage> watches = storageService.getWatchesOwnedByClientAndUser(request);
            Integer num = watches.size();

            return ResponseEntity.ok(num);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getWatchSoldByClientTotalExpenses")
    public ResponseEntity<Double> getWatchSoldByClientTotalExpenses(@RequestParam String userEmail, @RequestParam Long clientId) {
        try {
            BuysClientRequest request = new BuysClientRequest();
            request.setUserEmail(userEmail);
            request.setClientId(clientId);
            List<Storage> watches = storageService.getWatchesOwnedByClientAndUser(request);
            Double spese = 0.0;
            for (Storage watch : watches){
                spese += watch.getSellPrice();
            }
            return ResponseEntity.ok(spese);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/getWatchesOwnedByClientMonth")
    public Integer getWatchesByClientMonth(@RequestParam Long id, @RequestParam int month){
        return storageService.getWatchesOwnedByClientMonth(id, month);
    }
}
