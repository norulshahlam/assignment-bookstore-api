package com.shah.bookstoreapi.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.shah.bookstoreapi.model.response.MyResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static final String ERROR_CAUSED_BY = "Error caused by: {}";

    /**
     * User-defined exception for business related exceptions
     *
     * @param req
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MyException.class})
    @ResponseBody
    public MyResponse<String> handleBookException(HttpServletRequest req, MyException e) {
        log.error(ERROR_CAUSED_BY, e.getErrorMessage());
        return MyResponse.failureResponse(e.getErrorMessage());
    }

    /**
     * to handle constraint when validating request body from client input
     *
     * @param req
     * @param e
     * @return
     */

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<MyResponse<List<Errors>>> handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {

        String requestUri = req.getRequestURI();

        List<Errors> cause = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Errors.builder()
                        .fieldName(error.getField())
                        .errorMessage(error.getDefaultMessage())
                        .build())
                .toList();

        log.error(ERROR_CAUSED_BY, cause);
        MyResponse<List<Errors>> response = MyResponse.failureResponse(cause, "Validation failed for request URI: " + requestUri);
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * to handle improper formatting from client input
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ResponseEntity<MyResponse<String>> handleHttpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException e) {

        String requestUri = req.getRequestURI();

        String message = e.getMessage();

        log.error(ERROR_CAUSED_BY, message);
        MyResponse<String> response = MyResponse.failureResponse(message, "Validation failed for request URI: " + requestUri);
        return ResponseEntity.ok(response);
    }

    /**
     * to handle improper formatting from client input
     *
     * @param req
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidFormatException.class})
    @ResponseBody
    public MyResponse<String> handleHttpMessageNotReadableException(
            HttpServletRequest req, InvalidFormatException e) {
        String errorMessages = e.getLocalizedMessage();
        log.error(ERROR_CAUSED_BY, errorMessages);
        return MyResponse.failureResponse(errorMessages);
    }

    /**
     * For all other unexpected exceptions
     *
     * @param req
     * @param e
     * @return
     */

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public MyResponse<String> handleBaseException(HttpServletRequest req, Exception e) {
        String errorMessages = e.getMessage();
        log.error("requestUrl : {}, occurred an error : {}, e detail : {}", req.getRequestURI(), errorMessages, e);
        return MyResponse.failureResponse(errorMessages);
    }
}
