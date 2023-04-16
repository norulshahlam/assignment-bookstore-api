package com.shah.bookstoreapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookException extends RuntimeException{
    private final String errorMessage;
}
