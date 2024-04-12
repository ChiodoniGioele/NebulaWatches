package ch.nebulaWatches.nebulaWatchesAPI.team.controller;

import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.UserService;
import ch.nebulaWatches.nebulaWatchesAPI.team.model.Team;
import ch.nebulaWatches.nebulaWatchesAPI.team.model.TeamRequest;
import ch.nebulaWatches.nebulaWatchesAPI.team.service.TeamService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;

    @GetMapping("/getTeam/{email}")
    public List<Team> getTeamMembers(@PathVariable String email) {
        return teamService.getTeamByEmail(email);
    }

    @PostMapping("/add")
    public ResponseEntity<String> newTeam(@RequestBody TeamRequest request) {
        try {
            teamService.saveTeamMember(request);
            return ResponseEntity.ok("Team member saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add team member: " + e.getMessage());
        }
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable int id) {
        try {
            teamService.removeTeamMember((long)id);
            return ResponseEntity.ok("User removed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to remove team member: " + e.getMessage());
        }
    }

    @PostMapping("/updateTeam")
    public ResponseEntity<String> updateTeam(@RequestBody TeamRequest request) {
        try {
            teamService.updateTeamMember(request);
            return ResponseEntity.ok("Team member updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to updated team member: " + e.getMessage());
        }
    }

    @GetMapping("/countSold/{id}")
    public int countSoldByTeam(@PathVariable int id) {
        try {
            return teamService.countSold((long)id);
        } catch (Exception e) {
            return -1;
        }
    }

    @GetMapping("/countSoldMonth/{id}/{month}")
    public int countSoldByTeamLast(@PathVariable int id, @PathVariable int month) {
        try {
            return teamService.countSoldMonth((long)id, month);
        } catch (Exception e) {
            return 0;
        }
    }

}
