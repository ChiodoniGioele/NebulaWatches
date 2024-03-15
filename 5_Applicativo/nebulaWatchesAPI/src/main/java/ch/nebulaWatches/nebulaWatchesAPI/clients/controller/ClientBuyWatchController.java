package ch.nebulaWatches.nebulaWatchesAPI.clients.controller;

import ch.nebulaWatches.nebulaWatchesAPI.clients.error.ClientNotFoundException;
import ch.nebulaWatches.nebulaWatchesAPI.clients.model.Client;
import ch.nebulaWatches.nebulaWatchesAPI.clients.model.ClientBuyWatch;
import ch.nebulaWatches.nebulaWatchesAPI.clients.repository.ClientBuyWatchRepository;
import ch.nebulaWatches.nebulaWatchesAPI.clients.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/v1/bought")
public class ClientBuyWatchController {
    private final ClientBuyWatchRepository repository;

    ClientBuyWatchController(ClientBuyWatchRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    ResponseEntity<List<ClientBuyWatch>> all() {
        try{
            return ResponseEntity.ok(repository.findAll());
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    ResponseEntity<ClientBuyWatch> newBought(@RequestBody ClientBuyWatch clientBuyWatch) {
        try{
            return ResponseEntity.ok(repository.save(clientBuyWatch));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/{idClient}")
    ResponseEntity<List<ClientBuyWatch>> getById(@PathVariable Long idClient) {
        try{
            return ResponseEntity.ok(repository.findByClientId(idClient));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
