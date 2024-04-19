package ch.nebulaWatches.nebulaWatchesAPI.security.controllers;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.AdminRequest;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.Role;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.SearchRequest;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.AdminService;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;

    @GetMapping("/getUsers")
    public List<User> getUsers(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        if(token != null && adminService.isAdminByToken(token)){
            return adminService.getAllUsers();
        }else{
            return null;
        }
    }
    @GetMapping("/getUser/{id}")
    public User getUser(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        String token = headers.getFirst("Authorization");
        if(token != null && adminService.isAdminByToken(token)){
            return adminService.getUser(id);
        }else{
            return null;
        }
    }
    @GetMapping("/getRole/{userEmail}")
    public Role getUserRole(@RequestHeader HttpHeaders headers, @PathVariable String userEmail) {
        String token = headers.getFirst("Authorization");
        if(token != null && adminService.isAdminByToken(token)){
            return adminService.getRole(userEmail);
        }else{
            return null;
        }
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestHeader HttpHeaders headers, @RequestBody AdminRequest request) {
        try {
            String token = headers.getFirst("Authorization");
            if(token != null && adminService.isAdminByToken(token)){
                adminService.archive(request);
                return ResponseEntity.ok("User archived successfully.");
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Only admins are allowed to archive users.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to remove user: " + e.getMessage());
        }
    }

    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestHeader HttpHeaders headers, @RequestBody AdminRequest request) {
        try {
            String token = headers.getFirst("Authorization");
            if(token != null && adminService.isAdminByToken(token)){
                adminService.saveUser(request);
                return ResponseEntity.ok("User saved successfully.");
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Only admins are allowed to save users.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add user: " + e.getMessage());
        }
    }
    @PostMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestHeader HttpHeaders headers, @RequestBody AdminRequest request) {
        try {
            String token = headers.getFirst("Authorization");
            if(token != null && adminService.isAdminByToken(token)){
                adminService.updateUser(request);
                return ResponseEntity.ok("User updated successfully.");
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Only admins are allowed to update users.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to updated user: " + e.getMessage());
        }
    }

    @PostMapping("/searchUser")
    public List<User> searchUser(@RequestHeader HttpHeaders headers, @RequestBody SearchRequest searchRequest) {
        try {
            String token = headers.getFirst("Authorization");
            if(token != null && adminService.isAdminByToken(token)){
                return adminService.searchUsers(searchRequest.getSearchContent());
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    @GetMapping("/isEmailUsed/{email}")
    public boolean isEmailUsed(@PathVariable String email){
        return adminService.isEmailUsed(email);
    }

}
