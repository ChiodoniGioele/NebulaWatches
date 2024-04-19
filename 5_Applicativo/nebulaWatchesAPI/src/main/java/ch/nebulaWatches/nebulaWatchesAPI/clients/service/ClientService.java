package ch.nebulaWatches.nebulaWatchesAPI.clients.service;

import ch.nebulaWatches.nebulaWatchesAPI.utils.InputUtils;
import ch.nebulaWatches.nebulaWatchesAPI.clients.model.Client;
import ch.nebulaWatches.nebulaWatchesAPI.clients.model.ClientRequest;
import ch.nebulaWatches.nebulaWatchesAPI.clients.repository.ClientRepository;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.BuysClientRequest;
import ch.nebulaWatches.nebulaWatchesAPI.storage.model.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;
    private final UserRepository userRepository;

    public void saveClient(ClientRequest request){
        Client client = new Client();
        client.setEmail(InputUtils.testInput(request.getEmail()));
        client.setName(InputUtils.testInput(request.getName()));
        client.setSurname(InputUtils.testInput(request.getSurname()));
        client.setPhone(InputUtils.testInput(request.getPhone()));

        if(InputUtils.isEmailValid(request.getUserEmail())){
            User user = userRepository.findByEmail(InputUtils.testInput(request.getUserEmail()))
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            client.setUser(user);
        }else{
            throw new IllegalArgumentException("Email not valid");
        }


        repository.save(client);
    }


}
