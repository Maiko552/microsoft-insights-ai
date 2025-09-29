
package br.com.maikonspo.microsoftAI.common;

import br.com.maikonspo.microsoftAI.infrastructure.exception.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    record ErrorPayload(String code, String message, OffsetDateTime timestamp) {}

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorPayload> handleBusiness(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorPayload("BUSINESS_ERROR", ex.getMessage(), OffsetDateTime.now()));
    }

    @ExceptionHandler(GraphApiException.class)
    public ResponseEntity<ErrorPayload> handleGraph(GraphApiException ex) {
        var status = HttpStatus.resolve(ex.getStatus());
        if (status == null) status = HttpStatus.BAD_GATEWAY; // fallback
        String msg = ex.getMessage() + (ex.getResponseBody() != null ? " | " + ex.getResponseBody() : "");
        return ResponseEntity.status(status)
                .body(new ErrorPayload("GRAPH_ERROR", msg, OffsetDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorPayload> handleAny(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorPayload("UNEXPECTED_ERROR", ex.getMessage(), OffsetDateTime.now()));
    }
}
