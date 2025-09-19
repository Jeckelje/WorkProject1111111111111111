package sudexpert.gov.by.workproject.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sudexpert.gov.by.workproject.dto.error.AppError;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // ==== КАСТОМНЫЕ ИСКЛЮЧЕНИЯ ====
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<AppError> handleResourceNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<AppError> handleDuplicateResource(DuplicateResourceException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // ==== SPRING VALIDATION ====
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppError> handleValidationException(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return buildErrorResponse("Некорректные данные: " + errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AppError> handleConstraintViolation(ConstraintViolationException ex) {
        String errors = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));
        return buildErrorResponse("Ошибка валидации: " + errors, HttpStatus.BAD_REQUEST);
    }

    // ==== SPRING WEB ====
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<AppError> handleNotReadable(HttpMessageNotReadableException ex) {
        return buildErrorResponse("Некорректный формат входных данных.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<AppError> handleConversion(HttpMessageConversionException ex) {
        return buildErrorResponse("Ошибка преобразования данных: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<AppError> handleMissingParam(MissingServletRequestParameterException ex) {
        return buildErrorResponse("Отсутствует обязательный параметр: " + ex.getParameterName(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<AppError> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return buildErrorResponse("Метод запроса не поддерживается: " + ex.getMethod(), HttpStatus.METHOD_NOT_ALLOWED);
    }



    // ==== JPA / БАЗА ДАННЫХ ====
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<AppError> handleDataIntegrity(DataIntegrityViolationException ex) {
        return buildErrorResponse("Нарушение целостности данных: " + ex.getMostSpecificCause().getMessage(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<AppError> handleEmptyResult(EmptyResultDataAccessException ex) {
        return buildErrorResponse("Запись не найдена для удаления.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppError> handleEntityNotFound(EntityNotFoundException ex) {
        return buildErrorResponse("Сущность не найдена.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<AppError> handleTransaction(TransactionSystemException ex) {
        return buildErrorResponse("Ошибка транзакции: " + ex.getMostSpecificCause().getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ==== JAVA CORE ====
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AppError> handleIllegalArgument(IllegalArgumentException ex) {
        return buildErrorResponse("Некорректный аргумент: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<AppError> handleNullPointer(NullPointerException ex) {
        return buildErrorResponse("Внутренняя ошибка: NullPointerException", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ==== FALLBACK ====
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppError> handleGeneralException(Exception ex) {
        log.error("ПИЗДА");
        return buildErrorResponse("Произошла непредвиденная ошибка: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ==== ВСПОМОГАТЕЛЬНЫЙ МЕТОД ====
    private ResponseEntity<AppError> buildErrorResponse(String message, HttpStatus status) {
        AppError error = AppError.builder()
                .status(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(status).body(error);
    }
}
