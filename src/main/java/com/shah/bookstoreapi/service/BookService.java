package com.shah.bookstoreapi.service;

import com.shah.bookstoreapi.model.entity.Book;
import com.shah.bookstoreapi.model.request.CreateBookRequest;
import com.shah.bookstoreapi.model.request.UpdateBookRequest;
import com.shah.bookstoreapi.model.response.BookResponse;
import com.shah.bookstoreapi.model.response.CreateBookResponse;

import java.util.List;
import java.util.UUID;


/**
 * @author NORUL
 */
public interface BookService {

    BookResponse<CreateBookResponse> addBook(CreateBookRequest book);

    BookResponse<List<Book>> getBookByTitleAndOrAuthor(String title, String author);

    BookResponse<UUID> deleteBook(UUID isbn);

    BookResponse<Book> updateBook(UpdateBookRequest updateBook);
}
