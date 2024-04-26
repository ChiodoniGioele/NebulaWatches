package ch.nebulaWatches.nebulaWatchesAPI.storage.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class StorageExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public String handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        return "Maximum upload size exceeded. Please upload a smaller file.";
    }
}