package ch.nebulaWatches.nebulaWatchesAPI.clients.error;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}