package com.shah.bookstoreapi.helper;

import com.shah.bookstoreapi.model.entity.Book;
import com.shah.bookstoreapi.model.request.CreateBookRequest;
import com.shah.bookstoreapi.model.response.CreateBookResponse;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;

/**
 * @author NORUL
 */

public class ObjectMapper {

    public static Book bookRequestToBookMapper(CreateBookRequest bookRequest) {
        return Book.builder()
                .title(bookRequest.getTitle())
                .year(Year.parse(bookRequest.getYear()))
                .price(new BigDecimal(bookRequest.getPrice()))
                .genre(bookRequest.getGenre())
                .author(new ArrayList<>(bookRequest.getAuthor()))
                .build();
    }

    public static CreateBookResponse bookToBookResponseMapper(Book bookRequest) {

        return CreateBookResponse.builder()
                .isbn(bookRequest.getIsbn())
                .title(bookRequest.getTitle())
                .year(bookRequest.getYear())
                .price(bookRequest.getPrice())
                .genre(bookRequest.getGenre())
                .author(new ArrayList<>(bookRequest.getAuthor()))
                .build();
    }
}
