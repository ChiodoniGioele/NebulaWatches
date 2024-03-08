package ch.nebulaWatches.nebulaWatchesAPI.storage.controller;

import ch.nebulaWatches.nebulaWatchesAPI.security.service.UserService;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.FavouriteRequest;
import ch.nebulaWatches.nebulaWatchesAPI.storage.service.FavouriteService;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/favourite")
@RequiredArgsConstructor
public class FavouriteController {

    private final FavouriteService favouriteService;
    private final UserService userService;

    @GetMapping("/{userEmail}")
    public ResponseEntity<List<Watch>> getFavouritesWatchesByUser(@PathVariable String userEmail) {
        int userId = userService.getId(userEmail);
        List<Watch> watches = favouriteService.getWatchesByUserId(userId);
        return ResponseEntity.ok(watches);
    }
    @PostMapping("/addFavouriteWatch")
    public ResponseEntity<String> addFavouriteWatch(@RequestBody FavouriteRequest request) {
        try {
            favouriteService.addWatchToFavourites(request);
            return ResponseEntity.ok("Watch added to favourites successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add watch to favourites: " + e.getMessage());
        }
    }
    @PostMapping("/removeFavouriteWatch")
    public ResponseEntity<String> removeFavouriteWatch(@RequestBody FavouriteRequest request) {
        try {
            favouriteService.removeFromFavourites(request);
            return ResponseEntity.ok("Watch removed from favourites successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to remove watch from favourites: " + e.getMessage());
        }
    }
    @GetMapping("/checkFavourite/{reference}/{userEmail}")
    public ResponseEntity<Boolean> checkIfWatchIsFavourite(@PathVariable String reference, @PathVariable String userEmail) {
        boolean isFavorite = favouriteService.isWatchInFavorites(reference, userEmail);
        return ResponseEntity.ok(isFavorite);
    }
}
