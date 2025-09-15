package sudexpert.gov.by.workproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sudexpert.gov.by.workproject.dto.error.AppError;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<AppError> handlerResourceNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<AppError> handlerDuplicateResource(DuplicateResourceException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppError> handleValidationException(MethodArgumentNotValidException ex) {
        return buildErrorResponse("Некорректные данные в запросе.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AppError> handleIllegalArgument(IllegalArgumentException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<AppError>  handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        return  buildErrorResponse("АШИБКА!!!!!!!!!!!!!!!!",HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(Exception.class) // общий обработчик
    public ResponseEntity<AppError> handleGeneralException(Exception ex) {
        return buildErrorResponse("Произошла внутренняя ошибка. Пожалуйста, попробуйте позже.",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<AppError> buildErrorResponse(String message, HttpStatus status) {
        AppError error = AppError.builder()
                .status(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(status).body(error);
    }
}
