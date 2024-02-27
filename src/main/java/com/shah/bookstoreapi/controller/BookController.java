package com.shah.bookstoreapi.controller;

import com.shah.bookstoreapi.model.entity.Book;
import com.shah.bookstoreapi.model.request.CreateBookRequest;
import com.shah.bookstoreapi.model.request.UpdateBookRequest;
import com.shah.bookstoreapi.model.response.MyResponse;
import com.shah.bookstoreapi.model.response.CreateBookResponse;
import com.shah.bookstoreapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.shah.bookstoreapi.model.Constants.*;

/**
 * @author NORUL
 */
@RestController
@Slf4j
@RequestMapping(API_V1)
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping(GET_BOOK_BY_TITLE_AUTHOR)
    public ResponseEntity<MyResponse<List<Book>>> getBookByTitleAndOrAuthor(
            @Parameter(description = "Title of book", example = "Ghostbusters")
            @RequestParam
            @NotBlank
            String title,

            @Parameter(description = "Author of book", example = "Bob")
            @RequestParam
            @NotBlank
            String author
    ) {
        log.info("in BookController::get-book-by-title-author");
        log.info("title: {}, author: {}", title, author);
        MyResponse<List<Book>> foundBook = service.getBookByTitleAndOrAuthor(title, author);
        return ResponseEntity.status(HttpStatus.FOUND).body(foundBook);
    }

    @PostMapping(ADD_BOOK)
    public ResponseEntity<MyResponse<CreateBookResponse>> addBook(@Valid @RequestBody CreateBookRequest book) {
        log.info("in BookController::addBook");
        log.info("Book: {}", book);
        MyResponse<CreateBookResponse> savedBook = service.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @DeleteMapping(DELETE_BOOK + "/{isbn}")
    public ResponseEntity<MyResponse<UUID>> deleteBook(
            @Parameter(description = "unique id of book", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID isbn) {
        MyResponse<UUID> deleteSuccess = service.deleteBook(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(deleteSuccess);
    }

    @PutMapping(UPDATE_BOOK)
    public ResponseEntity<MyResponse<Book>> updateBook(@Valid @RequestBody UpdateBookRequest book) {
        MyResponse<Book> updatedBook = service.updateBook(book);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
    }
}
