package ch.nebulaWatches.nebulaWatchesAPI.clients.service;

import ch.nebulaWatches.nebulaWatchesAPI.clients.model.Client;
import ch.nebulaWatches.nebulaWatchesAPI.clients.model.ClientRequest;
import ch.nebulaWatches.nebulaWatchesAPI.clients.repository.ClientRepository;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;
    private final UserRepository userRepository;

    public void saveClient(ClientRequest request){
        Client client = new Client();
        client.setEmail(request.getEmail());
        client.setName(request.getName());
        client.setSurname(request.getSurname());
        client.setPhone(request.getPhone());

        User user = userRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        client.setUser(user);

        repository.save(client);
    }
}
