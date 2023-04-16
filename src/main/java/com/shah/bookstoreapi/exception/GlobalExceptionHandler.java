package com.shah.bookstoreapi.exception;

import com.shah.bookstoreapi.model.response.BookResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    @ExceptionHandler({BookException.class})
    @ResponseBody
    public BookResponse<String> handleBookException(HttpServletRequest req, BookException e) {
        log.error(ERROR_CAUSED_BY, e.getErrorMessage());
        return BookResponse.failureResponse(e.getErrorMessage());
    }

    /**
     * to handle constraint when validating request body from client input
     *
     * @param req
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public BookResponse<String> handleMethodArgumentNotValidException(
            HttpServletRequest req, MethodArgumentNotValidException e) {

        List<String> cause = new ArrayList<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            cause.add(error.getDefaultMessage());
        }
        for (ObjectError error : e.getBindingResult().getGlobalErrors()) {
            cause.add(error.getDefaultMessage());
        }
        String errorMessages = String.join(", ", cause);

        log.error(ERROR_CAUSED_BY, errorMessages);
        return BookResponse.failureResponse(errorMessages);
    }

    /**
     * to handle proper formatting from client input
     *
     * @param req
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidFormatException.class})
    @ResponseBody
    public BookResponse<String> handleHttpMessageNotReadableException(
            HttpServletRequest req, InvalidFormatException e) {
        String errorMessages = e.getLocalizedMessage();
        log.error(ERROR_CAUSED_BY, errorMessages);
        return BookResponse.failureResponse(errorMessages);
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
    public BookResponse<String> handleBaseException(HttpServletRequest req, Exception e) {
        String errorMessages = e.getMessage();
        log.error("requestUrl : {}, occurred an error : {}, e detail : {}", req.getRequestURI(), errorMessages, e);
        return BookResponse.failureResponse(errorMessages);
    }
}
