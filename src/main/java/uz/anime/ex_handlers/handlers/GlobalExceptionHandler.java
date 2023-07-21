package uz.anime.ex_handlers.handlers;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.anime.dtos.AppErrorDTO;
import uz.anime.dtos.ResponseDTO;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<Void>> handleUnknownExceptions(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 500);
        return ResponseEntity.badRequest().body(new ResponseDTO<>(error));
    }

    @ExceptionHandler({RuntimeException.class, CredentialsExpiredException.class})
    public ResponseEntity<ResponseDTO<Void>> handleRuntimeExceptions(RuntimeException e, HttpServletRequest request) {
        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 400);
        return ResponseEntity.badRequest().body(new ResponseDTO<>(error));
    }

    @ExceptionHandler({InsufficientAuthenticationException.class, ExpiredJwtException.class, DisabledException.class})
    public ResponseEntity<ResponseDTO<Void>> handleInsufficientAuthenticationException(RuntimeException e, HttpServletRequest request) {
        AppErrorDTO error = new AppErrorDTO(request.getRequestURI(), e.getMessage(), 403);
        return ResponseEntity.status(403).body(new ResponseDTO<>(error));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String errorMessage = "Input is not valid";
        Map<String, List<String>> errorBody = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorBody.compute(field, (s, values) -> {
                if (!Objects.isNull(values))
                    values.add(message);
                else
                    values = new ArrayList<>(Collections.singleton(message));
                return values;
            });
        }
        String errorPath = request.getRequestURI();
        AppErrorDTO error = new AppErrorDTO(errorPath, errorMessage, errorBody, 400);
        return ResponseEntity.status(400).body(new ResponseDTO<>(error));
    }
}
