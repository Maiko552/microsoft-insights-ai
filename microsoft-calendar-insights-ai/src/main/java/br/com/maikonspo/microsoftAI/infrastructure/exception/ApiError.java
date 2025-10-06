package br.com.maikonspo.microsoftAI.infrastructure.exception;

import java.time.OffsetDateTime;

public record ApiError(
        String code,
        String message,
        int status,
        String path,
        OffsetDateTime timestamp
) {}
