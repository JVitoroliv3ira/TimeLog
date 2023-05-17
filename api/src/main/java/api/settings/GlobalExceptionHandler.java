package api.settings;

import api.dtos.responses.ResponseDTO;
import api.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO<>(null, errors));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDTO<Object>> handleBadRequestException(BadRequestException ex) {
        Map<String, List<String>> error = new HashMap<>();
        error.put(ex.getField(), List.of(ex.getMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO<>(null, error));
    }
}
