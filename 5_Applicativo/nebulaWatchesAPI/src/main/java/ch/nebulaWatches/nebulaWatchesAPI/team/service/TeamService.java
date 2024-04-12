package ch.nebulaWatches.nebulaWatchesAPI.team.service;

import ch.nebulaWatches.nebulaWatchesAPI.utils.InputUtils;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import ch.nebulaWatches.nebulaWatchesAPI.storage.repository.StorageRepository;
import ch.nebulaWatches.nebulaWatchesAPI.team.model.Team;
import ch.nebulaWatches.nebulaWatchesAPI.team.model.TeamRequest;
import ch.nebulaWatches.nebulaWatchesAPI.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final StorageRepository storageRepository;


    public List<Team> getTeamByEmail(String email){
        return teamRepository.findAllByUserEmail(email);
    }

    public Optional<Team> getTeamMember(Long id){ return teamRepository.findById(id);}

    public void saveTeamMember(TeamRequest request){
        Team team = new Team();
        team.setEmail(InputUtils.testInput(request.getEmail()));
        team.setName(InputUtils.testInput(request.getName()));
        team.setSurname(InputUtils.testInput(request.getSurname()));
        team.setPhone(InputUtils.testInput(request.getPhone()));
        team.setRole(InputUtils.testInput(request.getRole()));

        if(InputUtils.isEmailValid(request.getUserEmail())){
            User user = userRepository.findByEmail(InputUtils.testInput(request.getUserEmail()))
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            team.setUser(user);
        }else{
            throw new IllegalArgumentException("User email not valid!");
        }
        teamRepository.save(team);
    }

    public void removeTeamMember(Long id) {
        teamRepository.deleteById(id);
    }

    public void updateTeamMember(TeamRequest request){
        Optional<Team> team = teamRepository.findById(request.getId());
        if (team.isPresent()) {
            Team team1 = team.get();
            team1.setId(request.getId());
            team1.setName(InputUtils.testInput(request.getName()));
            team1.setEmail(InputUtils.testInput(request.getEmail()));
            team1.setSurname(InputUtils.testInput(request.getSurname()));
            team1.setPhone(InputUtils.testInput(request.getPhone()));
            team1.setRole(InputUtils.testInput(request.getRole()));

            if(InputUtils.isEmailValid(request.getUserEmail())){
                User user = userRepository.findByEmail(InputUtils.testInput(request.getUserEmail()))
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));
                team1.setUser(user);
            }else{
                throw new IllegalArgumentException("User email not valid!");
            }
            teamRepository.save(team1);
        }
    }
    public int countSold(Long id){
        return storageRepository.sumQuantityByTeam(id);
    }
    public int countSoldMonth(Long id, int month){
        LocalDate l1 = getStartOfMonth(month);
        LocalDate l2 = getEndOfMonth(month);
        return storageRepository.sumQuantityByTeamMonth(id, l1, l2);
    }

    public static LocalDate getStartOfMonth(int months) {
        return LocalDate.now().minusMonths(months).with(TemporalAdjusters.firstDayOfMonth());
    }
    public static LocalDate getEndOfMonth(int months) {
        return LocalDate.now().minusMonths(months).with(TemporalAdjusters.lastDayOfMonth());
    }
}

