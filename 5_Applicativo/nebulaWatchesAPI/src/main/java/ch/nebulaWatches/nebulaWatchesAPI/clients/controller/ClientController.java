package ch.nebulaWatches.nebulaWatchesAPI.clients.controller;

import ch.nebulaWatches.nebulaWatchesAPI.clients.error.ClientNotFoundException;
import ch.nebulaWatches.nebulaWatchesAPI.clients.model.Client;
import ch.nebulaWatches.nebulaWatchesAPI.clients.model.ClientRequest;
import ch.nebulaWatches.nebulaWatchesAPI.clients.repository.ClientRepository;
import ch.nebulaWatches.nebulaWatchesAPI.clients.service.ClientService;
import ch.nebulaWatches.nebulaWatchesAPI.storage.repository.StorageRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Family;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository repository;
    private final StorageRepository storageRepository;
    private final ClientService clientService;

    @GetMapping("/all/{email}")
    ResponseEntity<List<Client>> all(@PathVariable String email) {
        try{
            return ResponseEntity.ok(repository.findAllByUserEmail(email));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<Optional<Client>> getById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(repository.findById(id));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> newClient(@RequestBody ClientRequest newClient) {
        try {
            clientService.saveClient(newClient);
            return ResponseEntity.ok("Client saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add client: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    void deleteClient(@PathVariable Long id) {
        //storageRepository.updateClientIdToNull(id);
        //repository.deleteById(id);
        Optional<Client> c = repository.findById(id);
        if(c.isPresent()){
            Client client = c.get();
            client.setStatus(false);
            repository.save(client);
        }

    }

    @PutMapping("/update/{id}")
    ResponseEntity<Client> replaceClient(@RequestBody Client newClient, @PathVariable Long id) {
        try{
            return ResponseEntity.ok(
                    repository.findById(id)
                    .map(client -> {
                        client.setName(newClient.getName());
                        client.setSurname(newClient.getSurname());
                        client.setPhone(newClient.getPhone());
                        return repository.save(client);
                    })
                    .orElseGet(() -> {
                        newClient.setId(id);
                        return repository.save(newClient);
                    })
            );
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
