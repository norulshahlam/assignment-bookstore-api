package com.shah.bookstoreapi.impl;

import com.shah.bookstoreapi.exception.MyException;
import com.shah.bookstoreapi.model.entity.Book;
import com.shah.bookstoreapi.model.request.CreateBookRequest;
import com.shah.bookstoreapi.model.request.UpdateBookRequest;
import com.shah.bookstoreapi.model.response.CreateBookResponse;
import com.shah.bookstoreapi.model.response.MyResponse;
import com.shah.bookstoreapi.repository.BookRepository;
import com.shah.bookstoreapi.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.shah.bookstoreapi.helper.ObjectMapper.bookRequestToBookMapper;
import static com.shah.bookstoreapi.helper.ObjectMapper.bookToBookResponseMapper;
import static com.shah.bookstoreapi.model.response.MyResponse.successResponse;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    public static final String ENTER_TITLE_OR_AUTHOR_NAME = "Enter title or author name";

    public static final String BOOK_NOT_FOUND = "Book not found";
    public static final String BOOK_FOUND = "Book found: {}";
    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public MyResponse<CreateBookResponse> addBook(CreateBookRequest book) {
        log.info("in BookService::addBook");

        Book book2 = bookRequestToBookMapper(book);
        Book savedBook = repository.save(book2);
        CreateBookResponse createBookResponse = bookToBookResponseMapper(savedBook);
        log.info("New book created: {}", savedBook);
        return successResponse(createBookResponse);
    }

    @Override
    public MyResponse<List<Book>> getBookByTitleAndOrAuthor(String title, String author) {
        log.info("in BookService::getBookByTitleAndOrAuthor");
        if (isNotBlank(title) && isNotBlank(author)) {
            return findByTitleAndAuthor(title, author);
        } else if (isNotBlank(title) && isBlank(author)) {
            return findByTitle(title);
        } else if (isBlank(title) && isNotBlank(author)) {
            return findByAuthor(author);
        } else {
            throw new MyException(ENTER_TITLE_OR_AUTHOR_NAME);
        }
    }

    @Override
    public MyResponse<UUID> deleteBook(UUID isbn) {
        log.info("in BookService::deleteBook");
        Optional<Book> book = repository.findById(isbn);
        if (book.isEmpty()) {
            throw new MyException(BOOK_NOT_FOUND);
        }
        repository.deleteById(isbn);
        return successResponse(isbn);
    }

    @Override
    public MyResponse<Book> updateBook(UpdateBookRequest updateBook) {
        log.info("in BookService::updateBook");
        Optional<Book> book = repository.findById(updateBook.getIsbn());
        if (book.isEmpty()) {
            throw new MyException(BOOK_NOT_FOUND);
        }
        BeanUtils.copyProperties(updateBook, book.get());
        Book savedBook = repository.save(book.get());
        return successResponse(savedBook);
    }

    /**
     * Search by author and/or by author using Jpa query method
     * Source: <a href="https://evonsdesigns.medium.com/spring-jpa-one-to-many-query-examples-281078bc457b">...</a>
     *
     * @param title  Book title
     * @param author Book author
     * @return List of books
     */

    private MyResponse<List<Book>> findByTitleAndAuthor(String title, String author) {
        log.info("Finding book by title: {} and author: {}", title, author);
        List<Book> byTitleAndAuthor = repository.findByTitleIgnoreCaseAndAuthorNameIgnoreCase(title, author);
        if (!byTitleAndAuthor.isEmpty()) {
            log.info(BOOK_FOUND, byTitleAndAuthor);
            return successResponse(byTitleAndAuthor);
        }
        log.info(BOOK_NOT_FOUND);
        throw new MyException(BOOK_NOT_FOUND);
    }

    private MyResponse<List<Book>> findByTitle(String title) {
        log.info("Finding book by title: {} ", title);
        List<Book> byTitleName = repository.findByTitleIgnoreCase(title);
        if (!byTitleName.isEmpty()) {
            log.info(BOOK_FOUND, byTitleName);
            return successResponse(byTitleName);
        }
        log.info(BOOK_NOT_FOUND);
        throw new MyException(BOOK_NOT_FOUND);
    }

    private MyResponse<List<Book>> findByAuthor(String author) {
        log.info("Finding book by author: {}", author);
        List<Book> byAuthorName = repository.findByAuthorNameIgnoreCase(author);
        if (!byAuthorName.isEmpty()) {
            log.info(BOOK_FOUND, byAuthorName);
            return successResponse(byAuthorName);
        }
        log.info(BOOK_NOT_FOUND);
        throw new MyException(BOOK_NOT_FOUND);
    }
}
