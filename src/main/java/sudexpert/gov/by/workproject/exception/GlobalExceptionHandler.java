package sudexpert.gov.by.workproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> handlerResourceNotFound(ResourceNotFoundException notFoundException){
        return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DuplicateResourceException.class})
    public ResponseEntity<String> handlerDuplicateResource(DuplicateResourceException duplicateResourceException){
        return new ResponseEntity<>(duplicateResourceException.getMessage(), HttpStatus.CONFLICT);
    }
}