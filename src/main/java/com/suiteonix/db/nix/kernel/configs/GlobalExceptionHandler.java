package com.suiteonix.db.nix.kernel.configs;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.suiteonix.db.nix.shared.exceptions.EX;
import com.suiteonix.db.nix.shared.exceptions.NixException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * <p>
 * This class provides centralized exception handling across all controllers in the application.
 * It extends Spring's ResponseEntityExceptionHandler and adds custom exception handling for
 * various types of exceptions that may occur during request processing.
 */
@Slf4j
@Configuration
@ControllerAdvice(basePackages = {"com.suiteonix"})
@RequiredArgsConstructor
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles constraint violation exceptions.
     *
     * @param e the constraint violation exception
     * @return a problem detail with BAD_REQUEST status and the exception message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * Handles data integrity violation exceptions.
     *
     * @param e the data integrity violation exception
     * @return a problem detail with CONFLICT status and a formatted error message
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Data integrity violation: " + e.getMessage());
    }

    /**
     * Handles bad credentials exceptions.
     *
     * @param ignored the bad credentials exception
     * @return a problem detail with UNAUTHORIZED status and an invalid credentials message
     */
    @ExceptionHandler(BadCredentialsException.class)
    ProblemDetail handleBadCredentialsException(BadCredentialsException ignored) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    /**
     * Handles access denied exceptions.
     *
     * @param ignored the access denied exception
     * @return a problem detail with FORBIDDEN status and access denied message
     */
    @ExceptionHandler(AccessDeniedException.class)
    ProblemDetail handleAccessDeniedException(AccessDeniedException ignored) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Access denied");
    }

    @ExceptionHandler(DataAccessException.class)
    ProblemDetail handleDataAccessException(DataAccessException e) {
        var exc = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        exc.setTitle("DATA_ACCESS_ERROR");
        exc.setProperties(Map.of("exception", e.getClass().getSimpleName()));
        return exc;
    }

    @ExceptionHandler(SQLException.class)
    ProblemDetail handleDbActionExecutionException(SQLException e) {
        var exc = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        exc.setTitle("DATABASE_ERROR");
        exc.setProperties(Map.of("exception", e.getClass().getSimpleName()));
        return exc;
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        var nixEx = EX.notFound("RESOURCE_NOT_FOUND", "The requested resource was not found")
                .note("Endpoint not found (Most Probable error): " + ex.getResourcePath())
                .toProblemDetail();
        return ResponseEntity.status(nixEx.getStatus()).body(nixEx);
//        return super.handleNoResourceFoundException(ex, headers, status, request);
    }


    /**
     * Handles method argument not valid exceptions.
     * <p>
     * This method processes validation errors from request body objects annotated with
     * {@code @Valid} and adds field errors to the response.
     *
     * @param ex      the method argument not valid exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a response entity with validation error details
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {

//        Stream<ErrorResponseDto.Error> errors = ex.getBindingResult().getAllErrors().stream().map((error) -> ErrorResponseDto.Error.FromFieldError((FieldError) error.));
//        ex.getBindingResult().getFieldErrors().forEach(error -> {})
        if (ex.getBody().getProperties() == null)
            ex.getBody().setProperties(new HashMap<>());
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            if (fieldError.getDefaultMessage() != null)
                ex.getBody().getProperties().put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        ex.getBody().getProperties().put(
                "fields",
                ex.getBindingResult().getFieldErrors().stream().map(fieldError ->
                        Map.of(fieldError.getField(), fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : ""))
        );
        return new ResponseEntity<>(ex.getBody(), status);
    }

    /**
     * Handles HTTP message not readable exceptions.
     * <p>
     * This method processes exceptions that occur when the request body cannot be read,
     * typically due to malformed JSON or type conversion errors.
     *
     * @param ex      the HTTP message not readable exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a response entity with error details
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        if (problem.getProperties() == null)
            problem.setProperties(new HashMap<>());
        if (ex.getCause() instanceof InvalidFormatException ife) {
            problem.getProperties().put("field", ife.getPath().getLast().getFieldName());
            problem.getProperties().put("value", ife.getValue());
            problem.getProperties().put("type", ife.getTargetType().getSimpleName());
        }
        return new ResponseEntity<>(problem, status);
    }

    /**
     * Handles all uncaught exceptions.
     * <p>
     * This is a fallback handler that processes any exception not handled by more specific handlers.
     *
     * @param e the exception
     * @return a problem detail with INTERNAL_SERVER_ERROR status and a generic error message
     */
    @ExceptionHandler(Exception.class)
    ProblemDetail handleAllUncaughtException(Exception e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage());
    }

    /**
     * Handles NixException instances.
     * <p>
     * This method converts the custom NixException to a ProblemDetail response.
     *
     * @param e the NixException
     * @return a problem detail based on the exception's properties
     */
    @ExceptionHandler(NixException.class)
    ProblemDetail handleNixException(NixException e) {
        return e.toProblemDetail();
    }

}
