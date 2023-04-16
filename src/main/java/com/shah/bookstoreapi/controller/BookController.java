package com.shah.bookstoreapi.controller;

import com.shah.bookstoreapi.model.entity.Book;
import com.shah.bookstoreapi.model.request.CreateBookRequest;
import com.shah.bookstoreapi.model.request.UpdateBookRequest;
import com.shah.bookstoreapi.model.response.BookResponse;
import com.shah.bookstoreapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.shah.bookstoreapi.model.Constants.*;

@RestController
@Slf4j
@RequestMapping(API_V1)
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping(GET_BOOK_BY_TITLE_AUTHOR)
    public ResponseEntity<BookResponse<List<Book>>> getBookByTitleAndOrAuthor(
            @Parameter(description = "Title of book", example = "Ghostbusters")
            @RequestParam(defaultValue = "") String title,
            @Parameter(description = "Author of book", example = "Bob")
            @RequestParam(defaultValue = "") String author
    ) {
        log.info("in BookController::get-book-by-title-author");
        log.info("title: {}, author: {}", title, author);
        BookResponse<List<Book>> foundBook = service.getBookByTitleAndOrAuthor(title, author);
        return ResponseEntity.status(HttpStatus.FOUND).body(foundBook);
    }

    @PostMapping(ADD_BOOK)
    public ResponseEntity<BookResponse<Book>> addBook(@Valid @RequestBody CreateBookRequest book) {
        log.info("in BookController::addBook");
        log.info("Book: {}", book);
        BookResponse<Book> savedBook = service.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @DeleteMapping(DELETE_BOOK + "/{isbn}")
    public ResponseEntity<BookResponse<UUID>> deleteBook(
            @Parameter(description = "unique id of book", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID isbn) {
        BookResponse<UUID> deleteSuccess = service.deleteBook(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(deleteSuccess);
    }

    @PutMapping(UPDATE_BOOK)
    public ResponseEntity<BookResponse<Book>> updateBook(@Valid @RequestBody UpdateBookRequest book) {
        BookResponse<Book> updatedBook = service.updateBook(book);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
    }
}
