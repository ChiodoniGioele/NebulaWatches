package ch.nebulaWatches.nebulaWatchesAPI.clients.controller;

import ch.nebulaWatches.nebulaWatchesAPI.clients.error.ClientNotFoundException;
import ch.nebulaWatches.nebulaWatchesAPI.clients.model.Client;
import ch.nebulaWatches.nebulaWatchesAPI.clients.repository.ClientRepository;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Family;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
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
@RequestMapping("/v1/client")
public class ClientController {

    private final ClientRepository repository;

    ClientController(ClientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    ResponseEntity<List<Client>> all() {
        try{
            return ResponseEntity.ok(repository.findAll());
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



/*    @GetMapping("/{id}")
    Client getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }*/
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
    ResponseEntity<Client> newClient(@RequestBody Client newClient) {
        try{
            return ResponseEntity.ok(repository.save(newClient));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    void deleteClient(@PathVariable Long id) {
        repository.deleteById(id);
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
