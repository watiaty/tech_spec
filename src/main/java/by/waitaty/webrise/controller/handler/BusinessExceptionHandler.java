package by.waitaty.webrise.controller.handler;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import by.waitaty.webrise.dto.ErrorResponseDto;
import by.waitaty.webrise.exception.AlreadySubscribedException;
import by.waitaty.webrise.exception.EmailAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponseDto notFoundExceptionHandler(RuntimeException exception,
        WebRequest request) {
        log.warn(exception.getMessage());

        return new ErrorResponseDto(exception.getMessage(), NOT_FOUND, request);
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler({
        EmailAlreadyExistsException.class,
        AlreadySubscribedException.class
    })
    public ErrorResponseDto conflictExceptionHandler(RuntimeException exception,
        WebRequest request) {
        log.warn(exception.getMessage());

        return new ErrorResponseDto(exception.getMessage(), CONFLICT, request);
    }

}
