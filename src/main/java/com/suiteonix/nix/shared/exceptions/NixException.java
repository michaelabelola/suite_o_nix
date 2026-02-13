package com.suiteonix.nix.shared.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.util.*;

/**
 * Custom exception class for application-specific exceptions.
 * <p>
 * NixException extends RuntimeException and provides a fluent API for building
 * structured error responses using Spring's ProblemDetail format. It includes
 * support for HTTP status codes, error titles, detailed descriptions, and
 * additional properties.
 * <p>
 * This exception is designed to work with the GlobalExceptionHandler to provide
 * consistent error responses across the application.
 */
public class NixException extends RuntimeException {

    /**
     * The Status.
     */
    HttpStatusCode status;
    /**
     * -- GETTER --
     * Gets the title of the exception.
     *
     */
    @Getter
    private String title;
    private String detail;
    private String message;
    private URI instance;
    private Map<String, Object> properties = new HashMap<>();
    private List<Map<String, String>> errorFields;
    private Set<Map<String, String>> serverErrorFields;
    private URI type;

    /**
     * Instantiates a new Nix exception.
     *
     * @param status the status
     */
    public NixException(HttpStatusCode status) {
        super(Objects.nonNull(status) ? String.valueOf(status.value()) : "");
        this.status = status;
    }

    /**
     * Instantiates a new Nix exception with a status and title.
     *
     * @param status the HTTP status code
     * @param title  the title of the exception
     */
    public NixException(HttpStatusCode status, String title) {
        super(Objects.nonNull(title) ? title : "");
        this.status = status;
    }

    /**
     * Title nix exception.
     *
     * @param title the title
     * @return the nix exception
     */
    public NixException title(String title) {
        this.title = title;
        return this;
    }

    public NixException type(URI type) {
        this.type = type;
        return this;
    }

    public NixException message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Detail nix exception.
     *
     * @param detail the detail
     * @return the nix exception
     */
    public NixException detail(String detail) {
        this.detail = detail;
        return this;
    }

    /**
     * Instance nix exception.
     *
     * @param instance the instance
     * @return the nix exception
     */
    public NixException instance(URI instance) {
        this.instance = instance;
        return this;
    }

    /**
     * set Properties to a new map (defaults to an empty Map).
     *
     * @param properties the properties
     * @return the nix exception
     */
    public NixException properties(Map<String, Object> properties) {
        this.properties = Objects.requireNonNullElseGet(properties, HashMap::new);
        return this;
    }

    public NixException errorFields(Map<String, String> values) {
        if (this.errorFields == null) this.errorFields = new ArrayList<>();

        if (values != null)
            this.errorFields.add(values.entrySet().stream().map(entry -> Map.of(entry.getKey(), entry.getValue())).findFirst().orElse(Collections.emptyMap()));
        return this;
    }

    public NixException serverErrors(Map<String, String> values) {
        if (this.serverErrorFields == null) this.serverErrorFields = new HashSet<>();

        if (values != null)
            this.serverErrorFields.add(values.entrySet().stream().map(entry -> Map.of(entry.getKey(), entry.getValue())).findFirst().orElse(Collections.emptyMap()));
        return this;
    }

    /**
     * Prop nix exception.
     *
     * @param key   the key
     * @param value the value
     * @return the nix exception
     */
    public NixException prop(String key, Object value) {
        if (this.properties == null)
            this.properties = new HashMap<>();
        if (key != null)
            this.properties.put(key, value);
        return this;
    }

    public NixException note(Object value) {
        if (this.properties == null)
            this.properties = new HashMap<>();
        this.properties.put("note", value);
        return this;
    }

    public NixException errorField(String key, String value) {
        if (this.errorFields == null)
            this.errorFields = new ArrayList<>();
        if (key != null)
            this.errorFields.add(Map.of(key, value));
        return this;
    }

    public NixException serverError(String key, String value) {
        if (this.serverErrorFields == null)
            this.serverErrorFields = new HashSet<>();
        if (key != null)
            this.serverErrorFields.add(Map.of(key, value));
        return this;
    }

    public NixException serverErrorMessage(String value) {
        if (this.serverErrorFields == null)
            this.serverErrorFields = new HashSet<>();
        this.serverErrorFields.add(Map.of("message", value));
        return this;
    }

    /**
     * Remove prop nix exception.
     *
     * @param key the key
     * @return the nix exception
     */
    public NixException removeProp(String key) {
        if (this.properties != null && key != null)
            this.properties.remove(key);
        return this;
    }


    public NixException removeError(String key) {
        if (this.errorFields != null && key != null)
            this.errorFields.removeIf(field -> field.containsKey(key));
        return this;
    }

    public NixException removeServerError(String key) {
        if (this.serverErrorFields != null && key != null)
            this.serverErrorFields.removeIf(field -> field.containsKey(key));
        return this;
    }

    /**
     * Instantiates a new Nix exception.
     *
     * @param status the status
     * @param body   the body
     */
    public NixException(HttpStatusCode status, ProblemDetail body) {
        this.status = status;
        if (body == null) return;
        this.title = body.getTitle();
        this.detail = body.getDetail();
        this.instance = body.getInstance();
        this.properties = body.getProperties();
    }

    /**
     * Instantiates a new Nix exception.
     *
     * @param body the body
     */
    public NixException(ProblemDetail body) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, body);
    }

    public ErrorResponseException buildException() {
        return new ErrorResponseException(status, toProblemDetail(), null);
    }

    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(this.status);
        Optional.ofNullable(this.type).ifPresent(problemDetail::setType);
        Optional.ofNullable(this.title).ifPresent(problemDetail::setTitle);
        Optional.ofNullable(this.detail).ifPresent(problemDetail::setDetail);
        Optional.ofNullable(this.instance).ifPresent(problemDetail::setInstance);

        Optional.ofNullable(this.errorFields).ifPresent(fields -> {
            if (fields.isEmpty()) return;
            if (this.properties == null) this.properties = new HashMap<>();
            this.properties.put("fields", fields);
        });
        Optional.ofNullable(this.serverErrorFields).ifPresent(fields -> {
            if (fields.isEmpty()) return;
            if (this.properties == null) this.properties = new HashMap<>();
            this.properties.put("serverError", fields);
        });
        if (this.properties != null) problemDetail.setProperties(this.properties);
        Optional.ofNullable(this.message).ifPresent(message -> problemDetail.setProperty("message", message));

        return problemDetail;
    }
}
