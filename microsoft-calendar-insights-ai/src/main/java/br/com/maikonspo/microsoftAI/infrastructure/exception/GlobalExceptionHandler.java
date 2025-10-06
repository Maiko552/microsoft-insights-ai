package br.com.maikonspo.microsoftAI.infrastructure.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ApiError build(String code, String message, int status, String path) {
        return new ApiError(code, message, status, path, OffsetDateTime.now());
    }

    @ExceptionHandler(GraphApiException.class)
    public ResponseEntity<ApiError> handleGraph(GraphApiException ex, HttpServletRequest req) {
        var err = build("GRAPH_ERROR", ex.getMessage(), HttpStatus.BAD_GATEWAY.value(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(err);
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<ApiError> handleOAuth(OAuth2AuthenticationException ex, HttpServletRequest req) {
        var err = build("OAUTH_ERROR", ex.getMessage(), HttpStatus.UNAUTHORIZED.value(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler({JwtException.class})
    public ResponseEntity<ApiError> handleJwt(JwtException ex, HttpServletRequest req) {
        var err = build("INVALID_TOKEN", ex.getMessage(), HttpStatus.UNAUTHORIZED.value(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleDenied(AccessDeniedException ex, HttpServletRequest req) {
        var err = build("FORBIDDEN", "Acesso negado", HttpStatus.FORBIDDEN.value(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        var msg = ex.getBindingResult().getAllErrors().stream()
                .findFirst().map(e -> e.getDefaultMessage()).orElse("Dados inválidos");
        var err = build("VALIDATION_ERROR", msg, HttpStatus.BAD_REQUEST.value(), req.getRequestURI());
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
        var err = build("UNEXPECTED_ERROR", "Erro inesperado", HttpStatus.INTERNAL_SERVER_ERROR.value(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}
