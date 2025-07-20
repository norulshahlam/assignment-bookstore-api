package com.shah.bookstoreapi.service;

import com.shah.bookstoreapi.model.entity.Book;
import com.shah.bookstoreapi.model.request.CreateBookRequest;
import com.shah.bookstoreapi.model.request.UpdateBookRequest;
import com.shah.bookstoreapi.model.response.CreateBookResponse;
import com.shah.bookstoreapi.model.response.MyResponse;

import java.util.List;
import java.util.UUID;


/**
 * @author NORUL
 */
public interface BookService {

    MyResponse<CreateBookResponse> addBook(CreateBookRequest book);

    MyResponse<List<Book>> getBookByTitleAndOrAuthor(String title, String author);

    MyResponse<UUID> deleteBook(UUID isbn);

    MyResponse<Book> updateBook(UpdateBookRequest updateBook);
}
