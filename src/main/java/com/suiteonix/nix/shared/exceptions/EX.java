package com.suiteonix.nix.shared.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception utility class that provides factory methods for creating {@link NixException} instances
 * for various HTTP status codes.
 * <p>
 * This class contains static methods that create and configure NixException objects with
 * appropriate HTTP status codes, titles, and details. Each method has two overloads:
 * one that accepts both title and details, and another that accepts only title.
 * <p>
 * The methods are organized by HTTP status code categories (4xx, 5xx, 1xx, 2xx, 3xx).
 */
public class EX {

    private EX() {
    }

    /**
     * Creates a NixException with HTTP 400 Bad Request status.
     *
     * @param title  the title of the exception, defaults to "Bad Request" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException badRequest(String title, String detail) {
        return new NixException(HttpStatus.BAD_REQUEST)
                .title(title != null ? title : "Bad Request")
                .detail(detail != null ? detail : "The request was invalid or cannot be processed");
    }

    /**
     * Creates a NixException with HTTP 400 Bad Request status.
     *
     * @param title the title of the exception, defaults to "Bad Request" if null
     * @return a configured NixException instance
     */
    public static NixException badRequest(String title) {
        return badRequest(title, null);
    }

    /**
     * Creates a NixException with HTTP 401 Unauthorized status.
     *
     * @param title  the title of the exception, defaults to "Unauthorized" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException unauthorized(String title, String detail) {
        return new NixException(HttpStatus.UNAUTHORIZED)
                .title(title != null ? title : "Unauthorized")
                .detail(detail != null ? detail : "Authentication is required and has failed or has not been provided");
    }

    /**
     * Creates a NixException with HTTP 401 Unauthorized status.
     *
     * @param title the title of the exception, defaults to "Unauthorized" if null
     * @return a configured NixException instance
     */
    public static NixException unauthorized(String title) {
        return unauthorized(title, null);
    }

    /**
     * Creates a NixException with HTTP 403 Forbidden status.
     *
     * @param title  the title of the exception, defaults to "Forbidden" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException forbidden(String title, String detail) {
        return new NixException(HttpStatus.FORBIDDEN)
                .title(title != null ? title : "Forbidden")
                .detail(detail != null ? detail : "Access to the requested resource is forbidden");
    }

    /**
     * Creates a NixException with HTTP 403 Forbidden status.
     *
     * @param title the title of the exception, defaults to "Forbidden" if null
     * @return a configured NixException instance
     */
    public static NixException forbidden(String title) {
        return forbidden(title, null);
    }

    /**
     * Creates a NixException with HTTP 404 Not Found status.
     *
     * @param title  the title of the exception defaults to "Not Found" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException notFound(String title, String detail) {
        return new NixException(HttpStatus.NOT_FOUND)
                .title(title != null ? title : "Not Found")
                .detail(detail != null ? detail : "The requested resource was not found");
    }

    /**
     * Creates a NixException with HTTP 404 Not Found status.
     *
     * @param detail the title of the exception, defaults to "NOT_FOUND" if null
     * @return a configured NixException instance
     */
    public static NixException notFound(String detail) {
        return notFound("NOT_FOUND", detail);
    }

    /**
     * Creates a NixException with HTTP 405 Method Not Allowed status.
     *
     * @param title  the title of the exception defaults to "Method Not Allowed" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException methodNotAllowed(String title, String detail) {
        return new NixException(HttpStatus.METHOD_NOT_ALLOWED)
                .title(title != null ? title : "Method Not Allowed")
                .detail(detail != null ? detail : "The requested HTTP method is not supported for this resource");
    }

    /**
     * Creates a NixException with HTTP 405 Method Not Allowed status.
     *
     * @param title the title of the exception, defaults to "Method Not Allowed" if null
     * @return a configured NixException instance
     */
    public static NixException methodNotAllowed(String title) {
        return methodNotAllowed(title, null);
    }

    /**
     * Creates a NixException with HTTP 409 Conflict status.
     *
     * @param title  the title of the exception, defaults to "Conflict" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException conflict(String title, String detail) {
        return new NixException(HttpStatus.CONFLICT)
                .title(title != null ? title : "Conflict")
                .detail(detail != null ? detail : "The request conflicts with the current state of the target resource");
    }

    /**
     * Creates a NixException with HTTP 409 Conflict status.
     *
     * @param title the title of the exception, defaults to "Conflict" if null
     * @return a configured NixException instance
     */
    public static NixException conflict(String title) {
        return conflict(title, null);
    }

    /**
     * Creates a NixException with HTTP 500 Internal Server Error status.
     *
     * @param title  the title of the exception, defaults to "Internal Server Error" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException internalServerError(String title, String detail) {
        return new NixException(HttpStatus.INTERNAL_SERVER_ERROR)
                .title(title != null ? title : "Internal Server Error")
                .detail(detail != null ? detail : "An unexpected error occurred while processing the request");
    }

    /**
     * Creates a NixException with HTTP 500 Internal Server Error status.
     *
     * @param title the title of the exception, defaults to "Internal Server Error" if null
     * @return a configured NixException instance
     */
    public static NixException internalServerError(String title) {
        return internalServerError(title, null);
    }

    /**
     * Creates a NixException with HTTP 503 Service Unavailable status.
     *
     * @param title  the title of the exception, defaults to "Service Unavailable" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException serviceUnavailable(String title, String detail) {
        return new NixException(HttpStatus.SERVICE_UNAVAILABLE)
                .title(title != null ? title : "Service Unavailable")
                .detail(detail != null ? detail : "The service is temporarily unavailable");
    }

    /**
     * Creates a NixException with HTTP 503 Service Unavailable status.
     *
     * @param title the title of the exception, defaults to "Service Unavailable" if null
     * @return a configured NixException instance
     */
    public static NixException serviceUnavailable(String title) {
        return serviceUnavailable(title, null);
    }

    // 1xx Informational

    /**
     * Creates a NixException with HTTP 100 Continue status.
     *
     * @param title  the title of the exception, defaults to "Continue" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException continue100(String title, String detail) {
        return new NixException(HttpStatus.CONTINUE)
                .title(title != null ? title : "Continue")
                .detail(detail != null ? detail : "The server has received the request headers and the client should proceed to send the request body");
    }

    /**
     * Creates a NixException with HTTP 100 Continue status.
     *
     * @param title the title of the exception, defaults to "Continue" if null
     * @return a configured NixException instance
     */
    public static NixException continue100(String title) {
        return continue100(title, null);
    }

    /**
     * Creates a NixException with HTTP 101 Switching Protocols status.
     *
     * @param title  the title of the exception, defaults to "Switching Protocols" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException switchingProtocols(String title, String detail) {
        return new NixException(HttpStatus.SWITCHING_PROTOCOLS)
                .title(title != null ? title : "Switching Protocols")
                .detail(detail != null ? detail : "The server is switching protocols according to the client's request");
    }

    /**
     * Creates a NixException with HTTP 101 Switching Protocols status.
     *
     * @param title the title of the exception, defaults to "Switching Protocols" if null
     * @return a configured NixException instance
     */
    public static NixException switchingProtocols(String title) {
        return switchingProtocols(title, null);
    }

//    /**
//     * Creates a NixException with HTTP 102 Processing status.
//     *
//     * @param title  the title of the exception, defaults to "Processing" if null
//     * @param details the detailed description of the exception, defaults to a standard message if null
//     * @return a configured NixException instance
//     */
//    public static NixException processing(String title, String details) {
//        return new NixException(HttpStatus.PROCESSING)
//                .title(title != null ? title : "Processing")
//                .details(details != null ? details : "The server is processing the request but no response is available yet");
//    }
//
//    /**
//     * Creates a NixException with HTTP 102 Processing status.
//     *
//     * @param title the title of the exception, defaults to "Processing" if null
//     * @return a configured NixException instance
//     */
//    public static NixException processing(String title) {
//        return processing(title, null);
//    }

//    public static NixException checkpoint (String title, String details) {
//        return new NixException(HttpStatus.CHECKPOINT)
//                .title(title != null ? title : "Checkpoint")
//                .details(details != null ? details: "The request should be retried after performing the appropriate action");
//    }

    // 2xx Success

    /**
     * Creates a NixException with HTTP 200 OK status.
     *
     * @param title  the title of the exception, defaults to "OK" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException ok(String title, String detail) {
        return new NixException(HttpStatus.OK)
                .title(title != null ? title : "OK")
                .detail(detail != null ? detail : "The request has succeeded");
    }

    /**
     * Creates a NixException with HTTP 200 OK status.
     *
     * @param title the title of the exception, defaults to "OK" if null
     * @return a configured NixException instance
     */
    public static NixException ok(String title) {
        return ok(title, null);
    }

    /**
     * Creates a NixException with HTTP 201 Created status.
     *
     * @param title  the title of the exception, defaults to "Created" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException created(String title, String detail) {
        return new NixException(HttpStatus.CREATED)
                .title(title != null ? title : "Created")
                .detail(detail != null ? detail : "The request has been fulfilled and a new resource has been created");
    }

    /**
     * Creates a NixException with HTTP 201 Created status.
     *
     * @param title the title of the exception, defaults to "Created" if null
     * @return a configured NixException instance
     */
    public static NixException created(String title) {
        return created(title, null);
    }

    /**
     * Creates a NixException with HTTP 202 Accepted status.
     *
     * @param title  the title of the exception, defaults to "Accepted" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException accepted(String title, String detail) {
        return new NixException(HttpStatus.ACCEPTED)
                .title(title != null ? title : "Accepted")
                .detail(detail != null ? detail : "The request has been accepted for processing, but the processing has not been completed");
    }

    /**
     * Creates a NixException with HTTP 202 Accepted status.
     *
     * @param title the title of the exception, defaults to "Accepted" if null
     * @return a configured NixException instance
     */
    public static NixException accepted(String title) {
        return accepted(title, null);
    }

    /**
     * Creates a NixException with HTTP 203 Non-Authoritative Information status.
     *
     * @param title  the title of the exception, defaults to "Non-Authoritative Information" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException nonAuthoritativeInformation(String title, String detail) {
        return new NixException(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
                .title(title != null ? title : "Non-Authoritative Information")
                .detail(detail != null ? detail : "The server is a transforming proxy that received a 200 OK from its origin but is returning a modified version of the origin's response");
    }

    /**
     * Creates a NixException with HTTP 203 Non-Authoritative Information status.
     *
     * @param title the title of the exception, defaults to "Non-Authoritative Information" if null
     * @return a configured NixException instance
     */
    public static NixException nonAuthoritativeInformation(String title) {
        return nonAuthoritativeInformation(title, null);
    }

    /**
     * Creates a NixException with HTTP 204 No Content status.
     *
     * @param title  the title of the exception, defaults to "No Content" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException noContent(String title, String detail) {
        return new NixException(HttpStatus.NO_CONTENT)
                .title(title != null ? title : "No Content")
                .detail(detail != null ? detail : "The server successfully processed the request but is not returning any content");
    }

    /**
     * Creates a NixException with HTTP 204 No Content status.
     *
     * @param title the title of the exception, defaults to "No Content" if null
     * @return a configured NixException instance
     */
    public static NixException noContent(String title) {
        return noContent(title, null);
    }

    /**
     * Creates a NixException with HTTP 205 Reset Content status.
     *
     * @param title  the title of the exception, defaults to "Reset Content" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException resetContent(String title, String detail) {
        return new NixException(HttpStatus.RESET_CONTENT)
                .title(title != null ? title : "Reset Content")
                .detail(detail != null ? detail : "The server successfully processed the request, but is not returning any content and requires that the requester reset the document view");
    }

    /**
     * Creates a NixException with HTTP 205 Reset Content status.
     *
     * @param title the title of the exception, defaults to "Reset Content" if null
     * @return a configured NixException instance
     */
    public static NixException resetContent(String title) {
        return resetContent(title, null);
    }

    /**
     * Creates a NixException with HTTP 206 Partial Content status.
     *
     * @param title  the title of the exception, defaults to "Partial Content" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException partialContent(String title, String detail) {
        return new NixException(HttpStatus.PARTIAL_CONTENT)
                .title(title != null ? title : "Partial Content")
                .detail(detail != null ? detail : "The server is delivering only part of the resource due to a range header sent by the client");
    }

    /**
     * Creates a NixException with HTTP 206 Partial Content status.
     *
     * @param title the title of the exception, defaults to "Partial Content" if null
     * @return a configured NixException instance
     */
    public static NixException partialContent(String title) {
        return partialContent(title, null);
    }

    /**
     * Creates a NixException with HTTP 207 Multi-Status status.
     *
     * @param title  the title of the exception, defaults to "Multi-Status" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException multiStatus(String title, String detail) {
        return new NixException(HttpStatus.MULTI_STATUS)
                .title(title != null ? title : "Multi-Status")
                .detail(detail != null ? detail : "The message body that follows is an XML message and can contain a number of separate response codes");
    }

    /**
     * Creates a NixException with HTTP 207 Multi-Status status.
     *
     * @param title the title of the exception, defaults to "Multi-Status" if null
     * @return a configured NixException instance
     */
    public static NixException multiStatus(String title) {
        return multiStatus(title, null);
    }

    /**
     * Creates a NixException with HTTP 208 Already Reported status.
     *
     * @param title  the title of the exception, defaults to "Already Reported" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException alreadyReported(String title, String detail) {
        return new NixException(HttpStatus.ALREADY_REPORTED)
                .title(title != null ? title : "Already Reported")
                .detail(detail != null ? detail : "The members of a DAV binding have already been enumerated in a previous reply to this request");
    }

    /**
     * Creates a NixException with HTTP 208 Already Reported status.
     *
     * @param title the title of the exception, defaults to "Already Reported" if null
     * @return a configured NixException instance
     */
    public static NixException alreadyReported(String title) {
        return alreadyReported(title, null);
    }

    /**
     * Creates a NixException with HTTP 226 IM Used status.
     *
     * @param title  the title of the exception, defaults to "IM Used" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException imUsed(String title, String detail) {
        return new NixException(HttpStatus.IM_USED)
                .title(title != null ? title : "IM Used")
                .detail(detail != null ? detail : "The server has fulfilled a request for the resource, and the response is a representation of the result of one or more instance-manipulations applied to the current instance");
    }

    /**
     * Creates a NixException with HTTP 226 IM Used status.
     *
     * @param title the title of the exception, defaults to "IM Used" if null
     * @return a configured NixException instance
     */
    public static NixException imUsed(String title) {
        return imUsed(title, null);
    }

    // 3xx Redirection

    /**
     * Creates a NixException with HTTP 300 Multiple Choices status.
     *
     * @param title  the title of the exception, defaults to "Multiple Choices" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException multipleChoices(String title, String detail) {
        return new NixException(HttpStatus.MULTIPLE_CHOICES)
                .title(title != null ? title : "Multiple Choices")
                .detail(detail != null ? detail : "The requested resource corresponds to any one of a set of representations, each with its own specific location");
    }

    /**
     * Creates a NixException with HTTP 300 Multiple Choices status.
     *
     * @param title the title of the exception, defaults to "Multiple Choices" if null
     * @return a configured NixException instance
     */
    public static NixException multipleChoices(String title) {
        return multipleChoices(title, null);
    }

    /**
     * Creates a NixException with HTTP 301 Moved Permanently status.
     *
     * @param title  the title of the exception, defaults to "Moved Permanently" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException movedPermanently(String title, String detail) {
        return new NixException(HttpStatus.MOVED_PERMANENTLY)
                .title(title != null ? title : "Moved Permanently")
                .detail(detail != null ? detail : "The requested resource has been assigned a new permanent URI");
    }

    /**
     * Creates a NixException with HTTP 301 Moved Permanently status.
     *
     * @param title the title of the exception, defaults to "Moved Permanently" if null
     * @return a configured NixException instance
     */
    public static NixException movedPermanently(String title) {
        return movedPermanently(title, null);
    }

    /**
     * Creates a NixException with HTTP 302 Found status.
     *
     * @param title  the title of the exception, defaults to "Found" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException found(String title, String detail) {
        return new NixException(HttpStatus.FOUND)
                .title(title != null ? title : "Found")
                .detail(detail != null ? detail : "The requested resource temporarily resides under a different URI");
    }

    /**
     * Creates a NixException with HTTP 302 Found status.
     *
     * @param title the title of the exception, defaults to "Found" if null
     * @return a configured NixException instance
     */
    public static NixException found(String title) {
        return found(title, null);
    }

    /**
     * Creates a NixException with HTTP 303 See Other status.
     *
     * @param title  the title of the exception, defaults to "See Other" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException seeOther(String title, String detail) {
        return new NixException(HttpStatus.SEE_OTHER)
                .title(title != null ? title : "See Other")
                .detail(detail != null ? detail : "The response to the request can be found under a different URI");
    }

    /**
     * Creates a NixException with HTTP 303 See Other status.
     *
     * @param title the title of the exception, defaults to "See Other" if null
     * @return a configured NixException instance
     */
    public static NixException seeOther(String title) {
        return seeOther(title, null);
    }

    /**
     * Creates a NixException with HTTP 304 Not Modified status.
     *
     * @param title  the title of the exception, defaults to "Not Modified" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException notModified(String title, String detail) {
        return new NixException(HttpStatus.NOT_MODIFIED)
                .title(title != null ? title : "Not Modified")
                .detail(detail != null ? detail : "The resource has not been modified since the version specified by the request headers");
    }

    /**
     * Creates a NixException with HTTP 304 Not Modified status.
     *
     * @param title the title of the exception, defaults to "Not Modified" if null
     * @return a configured NixException instance
     */
    public static NixException notModified(String title) {
        return notModified(title, null);
    }

//    public static NixException useProxy(String title, String details) {
//        return new NixException(HttpStatus.USE_PROXY)
//                .title(title != null ? title : "Use Proxy")
//                .details(details != null ? details: "The requested resource must be accessed through the proxy given by the Location field");
//    }

    /**
     * Creates a NixException with HTTP 307 Temporary Redirect status.
     *
     * @param title  the title of the exception, defaults to "Temporary Redirect" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException temporaryRedirect(String title, String detail) {
        return new NixException(HttpStatus.TEMPORARY_REDIRECT)
                .title(title != null ? title : "Temporary Redirect")
                .detail(detail != null ? detail : "The requested resource resides temporarily under a different URI");
    }

    /**
     * Creates a NixException with HTTP 307 Temporary Redirect status.
     *
     * @param title the title of the exception, defaults to "Temporary Redirect" if null
     * @return a configured NixException instance
     */
    public static NixException temporaryRedirect(String title) {
        return temporaryRedirect(title, null);
    }

    /**
     * Creates a NixException with HTTP 308 Permanent Redirect status.
     *
     * @param title  the title of the exception, defaults to "Permanent Redirect" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException permanentRedirect(String title, String detail) {
        return new NixException(HttpStatus.PERMANENT_REDIRECT)
                .title(title != null ? title : "Permanent Redirect")
                .detail(detail != null ? detail : "The requested resource has been assigned a new permanent URI");
    }

    /**
     * Creates a NixException with HTTP 308 Permanent Redirect status.
     *
     * @param title the title of the exception, defaults to "Permanent Redirect" if null
     * @return a configured NixException instance
     */
    public static NixException permanentRedirect(String title) {
        return permanentRedirect(title, null);
    }

    // 4xx Client Error - Some already implemented above

//    /**
//     * Creates a NixException with HTTP 413 Payload Too Large status.
//     *
//     * @param title  the title of the exception, defaults to "Payload Too Large" if null
//     * @param details the detailed description of the exception, defaults to a standard message if null
//     * @return a configured NixException instance
//     */
//    public static NixException payloadTooLarge(String title, String details) {
//        return new NixException(HttpStatus.PAYLOAD_TOO_LARGE)
//                .title(title != null ? title : "Payload Too Large")
//                .details(details != null ? details : "The server is refusing to process a request because the request payload is too large");
//    }
//
//    /**
//     * Creates a NixException with HTTP 413 Payload Too Large status.
//     *
//     * @param title the title of the exception, defaults to "Payload Too Large" if null
//     * @return a configured NixException instance
//     */
//    public static NixException payloadTooLarge(String title) {
//        return payloadTooLarge(title, null);
//    }

    /**
     * Creates a NixException with HTTP 414 URI Too Long status.
     *
     * @param title  the title of the exception, defaults to "URI Too Long" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException uriTooLong(String title, String detail) {
        return new NixException(HttpStatus.URI_TOO_LONG)
                .title(title != null ? title : "URI Too Long")
                .detail(detail != null ? detail : "The server is refusing to service the request because the request-target is too long");
    }

    /**
     * Creates a NixException with HTTP 414 URI Too Long status.
     *
     * @param title the title of the exception, defaults to "URI Too Long" if null
     * @return a configured NixException instance
     */
    public static NixException uriTooLong(String title) {
        return uriTooLong(title, null);
    }

    /**
     * Creates a NixException with HTTP 415 Unsupported Media Type status.
     *
     * @param title  the title of the exception, defaults to "Unsupported Media Type" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException unsupportedMediaType(String title, String detail) {
        return new NixException(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .title(title != null ? title : "Unsupported Media Type")
                .detail(detail != null ? detail : "The server is refusing to service the request because the entity of the request is in a format not supported by the requested resource");
    }

    /**
     * Creates a NixException with HTTP 415 Unsupported Media Type status.
     *
     * @param title the title of the exception, defaults to "Unsupported Media Type" if null
     * @return a configured NixException instance
     */
    public static NixException unsupportedMediaType(String title) {
        return unsupportedMediaType(title, null);
    }

    /**
     * Creates a NixException with HTTP 416 Requested Range Not Satisfiable status.
     *
     * @param title  the title of the exception, defaults to "Requested Range Not Satisfiable" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException requestedRangeNotSatisfiable(String title, String detail) {
        return new NixException(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
                .title(title != null ? title : "Requested Range Not Satisfiable")
                .detail(detail != null ? detail : "The client has asked for a portion of the file, but the server cannot supply that portion");
    }

    /**
     * Creates a NixException with HTTP 416 Requested Range Not Satisfiable status.
     *
     * @param title the title of the exception, defaults to "Requested Range Not Satisfiable" if null
     * @return a configured NixException instance
     */
    public static NixException requestedRangeNotSatisfiable(String title) {
        return requestedRangeNotSatisfiable(title, null);
    }

    /**
     * Creates a NixException with HTTP 417 Expectation Failed status.
     *
     * @param title  the title of the exception, defaults to "Expectation Failed" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException expectationFailed(String title, String detail) {
        return new NixException(HttpStatus.EXPECTATION_FAILED)
                .title(title != null ? title : "Expectation Failed")
                .detail(detail != null ? detail : "The server cannot meet the requirements of the Expect request-header field");
    }

    /**
     * Creates a NixException with HTTP 417 Expectation Failed status.
     *
     * @param title the title of the exception, defaults to "Expectation Failed" if null
     * @return a configured NixException instance
     */
    public static NixException expectationFailed(String title) {
        return expectationFailed(title, null);
    }

//    /**
//     * Creates a NixException with HTTP 418 I'm a teapot status.
//     *
//     * @param title  the title of the exception, defaults to "I'm a teapot" if null
//     * @param details the detailed description of the exception, defaults to a standard message if null
//     * @return a configured NixException instance
//     */
//    public static NixException iAmATeapot(String title, String details) {
//        return new NixException(HttpStatus.I_AM_A_TEAPOT)
//                .title(title != null ? title : "I'm a teapot")
//                .details(details != null ? details : "The server refuses to brew coffee because it is a teapot");
//    }
//
//    /**
//     * Creates a NixException with HTTP 418 I'm a teapot status.
//     *
//     * @param title the title of the exception, defaults to "I'm a teapot" if null
//     * @return a configured NixException instance
//     */
//    public static NixException iAmATeapot(String title) {
//        return iAmATeapot(title, null);
//    }

//    /**
//     * Creates a NixException with HTTP 422 Unprocessable Entity status.
//     *
//     * @param title  the title of the exception, defaults to "Unprocessable Entity" if null
//     * @param details the detailed description of the exception, defaults to a standard message if null
//     * @return a configured NixException instance
//     */
//    public static NixException unprocessableEntity(String title, String details) {
//        return new NixException(HttpStatus.UNPROCESSABLE_ENTITY)
//                .title(title != null ? title : "Unprocessable Entity")
//                .details(details != null ? details : "The server understands the content type of the request entity but was unable to process the contained instructions");
//    }
//
//    /**
//     * Creates a NixException with HTTP 422 Unprocessable Entity status.
//     *
//     * @param title the title of the exception, defaults to "Unprocessable Entity" if null
//     * @return a configured NixException instance
//     */
//    public static NixException unprocessableEntity(String title) {
//        return unprocessableEntity(title, null);
//    }

    /**
     * Creates a NixException with HTTP 423 Locked status.
     *
     * @param title  the title of the exception, defaults to "Locked" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException locked(String title, String detail) {
        return new NixException(HttpStatus.LOCKED)
                .title(title != null ? title : "Locked")
                .detail(detail != null ? detail : "The resource that is being accessed is locked");
    }

    /**
     * Creates a NixException with HTTP 423 Locked status.
     *
     * @param title the title of the exception, defaults to "Locked" if null
     * @return a configured NixException instance
     */
    public static NixException locked(String title) {
        return locked(title, null);
    }

    /**
     * Creates a NixException with HTTP 424 Failed Dependency status.
     *
     * @param title  the title of the exception, defaults to "Failed Dependency" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException failedDependency(String title, String detail) {
        return new NixException(HttpStatus.FAILED_DEPENDENCY)
                .title(title != null ? title : "Failed Dependency")
                .detail(detail != null ? detail : "The request failed due to failure of a previous request");
    }

    /**
     * Creates a NixException with HTTP 424 Failed Dependency status.
     *
     * @param title the title of the exception, defaults to "Failed Dependency" if null
     * @return a configured NixException instance
     */
    public static NixException failedDependency(String title) {
        return failedDependency(title, null);
    }

    /**
     * Creates a NixException with HTTP 425 Too Early status.
     *
     * @param title  the title of the exception, defaults to "Too Early" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException tooEarly(String title, String detail) {
        return new NixException(HttpStatus.TOO_EARLY)
                .title(title != null ? title : "Too Early")
                .detail(detail != null ? detail : "The server is unwilling to risk processing a request that might be replayed");
    }

    /**
     * Creates a NixException with HTTP 425 Too Early status.
     *
     * @param title the title of the exception, defaults to "Too Early" if null
     * @return a configured NixException instance
     */
    public static NixException tooEarly(String title) {
        return tooEarly(title, null);
    }

    /**
     * Creates a NixException with HTTP 426 Upgrade Required status.
     *
     * @param title  the title of the exception, defaults to "Upgrade Required" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException upgradeRequired(String title, String detail) {
        return new NixException(HttpStatus.UPGRADE_REQUIRED)
                .title(title != null ? title : "Upgrade Required")
                .detail(detail != null ? detail : "The client should switch to a different protocol");
    }

    /**
     * Creates a NixException with HTTP 426 Upgrade Required status.
     *
     * @param title the title of the exception, defaults to "Upgrade Required" if null
     * @return a configured NixException instance
     */
    public static NixException upgradeRequired(String title) {
        return upgradeRequired(title, null);
    }

    /**
     * Creates a NixException with HTTP 428 Precondition Required status.
     *
     * @param title  the title of the exception, defaults to "Precondition Required" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException preconditionRequired(String title, String detail) {
        return new NixException(HttpStatus.PRECONDITION_REQUIRED)
                .title(title != null ? title : "Precondition Required")
                .detail(detail != null ? detail : "The server requires the request to be conditional");
    }

    /**
     * Creates a NixException with HTTP 428 Precondition Required status.
     *
     * @param title the title of the exception, defaults to "Precondition Required" if null
     * @return a configured NixException instance
     */
    public static NixException preconditionRequired(String title) {
        return preconditionRequired(title, null);
    }

    /**
     * Creates a NixException with HTTP 429 Too Many Requests status.
     *
     * @param title  the title of the exception, defaults to "Too Many Requests" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException tooManyRequests(String title, String detail) {
        return new NixException(HttpStatus.TOO_MANY_REQUESTS)
                .title(title != null ? title : "Too Many Requests")
                .detail(detail != null ? detail : "The user has sent too many requests in a given amount of time");
    }

    /**
     * Creates a NixException with HTTP 429 Too Many Requests status.
     *
     * @param title the title of the exception, defaults to "Too Many Requests" if null
     * @return a configured NixException instance
     */
    public static NixException tooManyRequests(String title) {
        return tooManyRequests(title, null);
    }

    /**
     * Creates a NixException with HTTP 431 Request Header Fields Too Large status.
     *
     * @param title  the title of the exception, defaults to "Request Header Fields Too Large" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException requestHeaderFieldsTooLarge(String title, String detail) {
        return new NixException(HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE)
                .title(title != null ? title : "Request Header Fields Too Large")
                .detail(detail != null ? detail : "The server is unwilling to process the request because its header fields are too large");
    }

    /**
     * Creates a NixException with HTTP 431 Request Header Fields Too Large status.
     *
     * @param title the title of the exception, defaults to "Request Header Fields Too Large" if null
     * @return a configured NixException instance
     */
    public static NixException requestHeaderFieldsTooLarge(String title) {
        return requestHeaderFieldsTooLarge(title, null);
    }

    /**
     * Creates a NixException with HTTP 451 Unavailable For Legal Reasons status.
     *
     * @param title  the title of the exception, defaults to "Unavailable For Legal Reasons" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException unavailableForLegalReasons(String title, String detail) {
        return new NixException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
                .title(title != null ? title : "Unavailable For Legal Reasons")
                .detail(detail != null ? detail : "The server is denying access to the resource as a consequence of a legal demand");
    }

    /**
     * Creates a NixException with HTTP 451 Unavailable For Legal Reasons status.
     *
     * @param title the title of the exception, defaults to "Unavailable For Legal Reasons" if null
     * @return a configured NixException instance
     */
    public static NixException unavailableForLegalReasons(String title) {
        return unavailableForLegalReasons(title, null);
    }

    // 5xx Server Error - Some already implemented above

    /**
     * Creates a NixException with HTTP 501 Not Implemented status.
     *
     * @param title  the title of the exception, defaults to "Not Implemented" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException notImplemented(String title, String detail) {
        return new NixException(HttpStatus.NOT_IMPLEMENTED)
                .title(title != null ? title : "Not Implemented")
                .detail(detail != null ? detail : "The server does not support the functionality required to fulfill the request");
    }

    /**
     * Creates a NixException with HTTP 501 Not Implemented status.
     *
     * @param title the title of the exception, defaults to "Not Implemented" if null
     * @return a configured NixException instance
     */
    public static NixException notImplemented(String title) {
        return notImplemented(title, null);
    }

    /**
     * Creates a NixException with HTTP 502 Bad Gateway status.
     *
     * @param title  the title of the exception, defaults to "Bad Gateway" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException badGateway(String title, String detail) {
        return new NixException(HttpStatus.BAD_GATEWAY)
                .title(title != null ? title : "Bad Gateway")
                .detail(detail != null ? detail : "The server, while acting as a gateway or proxy, received an invalid response from the upstream server");
    }

    /**
     * Creates a NixException with HTTP 502 Bad Gateway status.
     *
     * @param title the title of the exception, defaults to "Bad Gateway" if null
     * @return a configured NixException instance
     */
    public static NixException badGateway(String title) {
        return badGateway(title, null);
    }

    /**
     * Creates a NixException with HTTP 504 Gateway Timeout status.
     *
     * @param title  the title of the exception, defaults to "Gateway Timeout" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException gatewayTimeout(String title, String detail) {
        return new NixException(HttpStatus.GATEWAY_TIMEOUT)
                .title(title != null ? title : "Gateway Timeout")
                .detail(detail != null ? detail : "The server, while acting as a gateway or proxy, did not receive a timely response from the upstream server");
    }

    /**
     * Creates a NixException with HTTP 504 Gateway Timeout status.
     *
     * @param title the title of the exception, defaults to "Gateway Timeout" if null
     * @return a configured NixException instance
     */
    public static NixException gatewayTimeout(String title) {
        return gatewayTimeout(title, null);
    }

    /**
     * Creates a NixException with HTTP 505 HTTP Version Not Supported status.
     *
     * @param title  the title of the exception, defaults to "HTTP Version Not Supported" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException httpVersionNotSupported(String title, String detail) {
        return new NixException(HttpStatus.HTTP_VERSION_NOT_SUPPORTED)
                .title(title != null ? title : "HTTP Version Not Supported")
                .detail(detail != null ? detail : "The server does not support the HTTP protocol version used in the request");
    }

    /**
     * Creates a NixException with HTTP 505 HTTP Version Not Supported status.
     *
     * @param title the title of the exception, defaults to "HTTP Version Not Supported" if null
     * @return a configured NixException instance
     */
    public static NixException httpVersionNotSupported(String title) {
        return httpVersionNotSupported(title, null);
    }

    /**
     * Creates a NixException with HTTP 506 Variant Also Negotiates status.
     *
     * @param title  the title of the exception, defaults to "Variant Also Negotiates" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException variantAlsoNegotiates(String title, String detail) {
        return new NixException(HttpStatus.VARIANT_ALSO_NEGOTIATES)
                .title(title != null ? title : "Variant Also Negotiates")
                .detail(detail != null ? detail : "The server has an external configuration error: the chosen variant resource is configured to engage in transparent content negotiation itself");
    }

    /**
     * Creates a NixException with HTTP 506 Variant Also Negotiates status.
     *
     * @param title the title of the exception, defaults to "Variant Also Negotiates" if null
     * @return a configured NixException instance
     */
    public static NixException variantAlsoNegotiates(String title) {
        return variantAlsoNegotiates(title, null);
    }

    /**
     * Creates a NixException with HTTP 507 Insufficient Storage status.
     *
     * @param title  the title of the exception, defaults to "Insufficient Storage" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException insufficientStorage(String title, String detail) {
        return new NixException(HttpStatus.INSUFFICIENT_STORAGE)
                .title(title != null ? title : "Insufficient Storage")
                .detail(detail != null ? detail : "The server has an external configuration error: the chosen variant resource is configured to engage in transparent content negotiation itself");
    }

    /**
     * Creates a NixException with HTTP 507 Insufficient Storage status.
     *
     * @param title the title of the exception, defaults to "Insufficient Storage" if null
     * @return a configured NixException instance
     */
    public static NixException insufficientStorage(String title) {
        return insufficientStorage(title, null);
    }

    /**
     * Creates a NixException with HTTP 508 Loop Detected status.
     *
     * @param title  the title of the exception, defaults to "Loop Detected" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException loopDetected(String title, String detail) {
        return new NixException(HttpStatus.LOOP_DETECTED)
                .title(title != null ? title : "Loop Detected")
                .detail(detail != null ? detail : "The server detected an infinite loop while processing the request");
    }

    /**
     * Creates a NixException with HTTP 508 Loop Detected status.
     *
     * @param title the title of the exception, defaults to "Loop Detected" if null
     * @return a configured NixException instance
     */
    public static NixException loopDetected(String title) {
        return loopDetected(title, null);
    }

//    /**
//     * Creates a NixException with HTTP 509 Bandwidth Limit Exceeded status.
//     *
//     * @param title  the title of the exception, defaults to "Bandwidth Limit Exceeded" if null
//     * @param details the detailed description of the exception, defaults to a standard message if null
//     * @return a configured NixException instance
//     */
//    public static NixException bandwidthLimitExceeded(String title, String details) {
//        return new NixException(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
//                .title(title != null ? title : "Bandwidth Limit Exceeded")
//                .details(details != null ? details : "The server has exceeded the bandwidth specified by the server administrator");
//    }
//
//    /**
//     * Creates a NixException with HTTP 509 Bandwidth Limit Exceeded status.
//     *
//     * @param title the title of the exception, defaults to "Bandwidth Limit Exceeded" if null
//     * @return a configured NixException instance
//     */
//    public static NixException bandwidthLimitExceeded(String title) {
//        return bandwidthLimitExceeded(title, null);
//    }

//    /**
//     * Creates a NixException with HTTP 510 Not Extended status.
//     *
//     * @param title  the title of the exception, defaults to "Not Extended" if null
//     * @param details the detailed description of the exception, defaults to a standard message if null
//     * @return a configured NixException instance
//     */
//    public static NixException notExtended(String title, String details) {
//        return new NixException(HttpStatus.NOT_EXTENDED)
//                .title(title != null ? title : "Not Extended")
//                .details(details != null ? details : "Further extensions to the request are required for the server to fulfill it");
//    }
//
//    /**
//     * Creates a NixException with HTTP 510 Not Extended status.
//     *
//     * @param title the title of the exception, defaults to "Not Extended" if null
//     * @return a configured NixException instance
//     */
//    public static NixException notExtended(String title) {
//        return notExtended(title, null);
//    }

    /**
     * Creates a NixException with HTTP 511 Network Authentication Required status.
     *
     * @param title  the title of the exception, defaults to "Network Authentication Required" if null
     * @param detail the detailed description of the exception, defaults to a standard message if null
     * @return a configured NixException instance
     */
    public static NixException networkAuthenticationRequired(String title, String detail) {
        return new NixException(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
                .title(title != null ? title : "Network Authentication Required")
                .detail(detail != null ? detail : "The client needs to authenticate to gain network access");
    }

    /**
     * Creates a NixException with HTTP 511 Network Authentication Required status.
     *
     * @param title the title of the exception, defaults to "Network Authentication Required" if null
     * @return a configured NixException instance
     */
    public static NixException networkAuthenticationRequired(String title) {
        return networkAuthenticationRequired(title, null);
    }
}
