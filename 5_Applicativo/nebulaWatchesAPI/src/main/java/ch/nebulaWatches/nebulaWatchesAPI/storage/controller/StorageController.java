package ch.nebulaWatches.nebulaWatchesAPI.storage.controller;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.UserService;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.CustomWatch;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.CustomWatchRequest;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Storage;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.StorageRequest;
import ch.nebulaWatches.nebulaWatchesAPI.storage.service.CustomWatchService;
import ch.nebulaWatches.nebulaWatchesAPI.storage.service.StatusStorageService;
import ch.nebulaWatches.nebulaWatchesAPI.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.util.List;

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

    @PostMapping("/saveCustom")
    public ResponseEntity<String> addCustomWatch(@RequestBody CustomWatchRequest request) {
        try {
            customWatchService.addCustomWatch(request);
            return ResponseEntity.ok("Watch added to storage successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add watch to storage: " + e.getMessage());
        }
    }
    @GetMapping("/custom/{userEmail}")
    public ResponseEntity<List<CustomWatch>> getCustomWatches(@PathVariable String userEmail) {
        int userId = userService.getId(userEmail);
        List<CustomWatch> watches = customWatchService.getWatchesByUserId(userId);
        return ResponseEntity.ok(watches);
    }
}
