package com.suiteonix.nix.kernel.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suiteonix.nix.shared.exceptions.NixException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CustomErrorController implements ErrorController {

    private final ObjectMapper objectMapper;

    public CustomErrorController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RequestMapping("/error")
    public ResponseEntity<ProblemDetail> handleError(HttpServletRequest request) {

        if (request.getAttribute("jakarta.servlet.error.exception") instanceof NixException ex) {
            throw ex;
        }

        Object statusAttr = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object messageAttr = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        HttpStatus status = statusAttr != null
                ? HttpStatus.resolve(Integer.parseInt(statusAttr.toString()))
                : HttpStatus.INTERNAL_SERVER_ERROR;
        if (status == null) status = HttpStatus.INTERNAL_SERVER_ERROR;

        String detail = (messageAttr != null && !messageAttr.toString().isBlank())
                ? messageAttr.toString()
                : status.getReasonPhrase();

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(status.getReasonPhrase());

        return ResponseEntity.status(status).body(problem);
    }
}
