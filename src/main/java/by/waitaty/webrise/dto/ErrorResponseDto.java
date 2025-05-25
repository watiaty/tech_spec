package by.waitaty.webrise.dto;

import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public record ErrorResponseDto(
    String error,
    int status,
    String path,
    OffsetDateTime timestamp
) {
    public ErrorResponseDto(String error, HttpStatus status, WebRequest request) {
        this(error, status.value(), ((ServletWebRequest) request).getRequest().getRequestURI(), OffsetDateTime.now());
    }
}
