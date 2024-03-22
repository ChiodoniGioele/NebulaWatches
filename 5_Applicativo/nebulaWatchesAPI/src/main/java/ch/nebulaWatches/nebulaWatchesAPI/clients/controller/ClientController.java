package ch.nebulaWatches.nebulaWatchesAPI.clients.controller;

import ch.nebulaWatches.nebulaWatchesAPI.clients.error.ClientNotFoundException;
import ch.nebulaWatches.nebulaWatchesAPI.clients.model.Client;
import ch.nebulaWatches.nebulaWatchesAPI.clients.model.ClientRequest;
import ch.nebulaWatches.nebulaWatchesAPI.clients.repository.ClientRepository;
import ch.nebulaWatches.nebulaWatchesAPI.security.models.User;
import ch.nebulaWatches.nebulaWatchesAPI.security.service.UserService;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Family;
import ch.nebulaWatches.nebulaWatchesAPI.watches.model.Watch;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/v1/clients")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientRepository repository;

    private final UserService userService;

    ClientController(ClientRepository repository, UserService userService) {
        this.repository = repository;
        this.userService =  userService;
    }

    // funziona
    @GetMapping("/all")
    ResponseEntity<List<Client>> all(@RequestBody ClientRequest request) {
        try{
            return ResponseEntity.ok(repository.findAllByUserId(request.getUser_email()));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // funziona
    @GetMapping("/get")
    ResponseEntity<Optional<Client>> getById(@RequestBody ClientRequest request) {
        try{
            return ResponseEntity.ok(repository.findByClientId(request.getUser_email(), request.getClient().getId()));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    ResponseEntity<Client> newClient(@RequestBody ClientRequest request) {
        Client c = request.getClient();
        User user = new User();
        user.setId(userService.getId(request.getUser_email()));
        c.setUser(user);
        // logger.error(newClient.getUser().toString());
        try{
            return ResponseEntity.ok(repository.save(c));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/delete")
    void deleteClient(@RequestBody ClientRequest request) {
        repository.deleteClientById(request.getUser_email(), request.getClient().getId());
    }

    @PostMapping("/update")
    ResponseEntity<Optional<Client>> replaceClient(@RequestBody ClientRequest request) {
        try{
            repository.updateCLient(request.getClient().getId(), request.getClient().getName(), request.getClient().getSurname(), request.getClient().getEmail(), request.getClient().getNotes(), request.getUser_email());
            return ResponseEntity.ok(getById(request)).getBody();
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
