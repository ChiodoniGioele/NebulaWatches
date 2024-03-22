package ch.nebulaWatches.nebulaWatchesAPI.security.controllers;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.AdminRequest;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.Role;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.AdminService;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.FavouriteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return adminService.getAllUsers();
    }
    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable int id) {
        return adminService.getUser(id);
    }
    @GetMapping("/getRole/{userEmail}")
    public Role getUserRole(@PathVariable String userEmail) {
        return adminService.getRole(userEmail);
    }
    @PostMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestBody AdminRequest request) {
        try {
            adminService.removeUser(request);
            return ResponseEntity.ok("User removed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to remove user: " + e.getMessage());
        }
    }
    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody AdminRequest request) {
        try {
            adminService.saveUser(request);
            return ResponseEntity.ok("User saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add user: " + e.getMessage());
        }
    }
    @PostMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody AdminRequest request) {
        try {
            adminService.updateUser(request);
            return ResponseEntity.ok("User updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to updated user: " + e.getMessage());
        }
    }
    @PostMapping("/searchUser/{searchContent}")
    public ResponseEntity<List<User>> searchUser(@PathVariable String searchContent) {
        try {
            List<User> users = adminService.searchUsers(searchContent);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
