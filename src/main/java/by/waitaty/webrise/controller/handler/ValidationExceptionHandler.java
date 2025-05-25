package by.waitaty.webrise.controller.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import by.waitaty.webrise.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseDto methodArgumentNotValidException(MethodArgumentNotValidException exception,
        WebRequest request) {
        String message = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining("; "));

        log.warn("Ошибка при валидации входных параметров: {}", message);

        return new ErrorResponseDto(message, BAD_REQUEST, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseDto constraintViolationException(ConstraintViolationException exception,
        WebRequest request) {
        String message = exception.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining("; "));

        log.warn("Ошибка при валидации параметров: {}", message);

        return new ErrorResponseDto(message, BAD_REQUEST, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseDto handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String paramName = ex.getName();
        String expectedType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "Unknown";
        String message = String.format("Параметр '%s' должен быть типа %s", paramName, expectedType);

        log.warn("Ошибка приведения типа параметра: {}", message);

        return new ErrorResponseDto(message, BAD_REQUEST, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseDto handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        String message = "Невозможно прочитать тело запроса: " + ex.getMostSpecificCause().getMessage();
        log.warn("Ошибка чтения запроса: {}", message);
        return new ErrorResponseDto(message, BAD_REQUEST, request);
    }

}
