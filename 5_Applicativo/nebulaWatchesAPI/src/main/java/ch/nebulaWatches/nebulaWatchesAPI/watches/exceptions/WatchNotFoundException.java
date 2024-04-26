package ch.nebulaWatches.nebulaWatchesAPI.watches.exceptions;

public class WatchNotFoundException extends RuntimeException{
    public WatchNotFoundException(String message) {
        super(message);
    }
}
