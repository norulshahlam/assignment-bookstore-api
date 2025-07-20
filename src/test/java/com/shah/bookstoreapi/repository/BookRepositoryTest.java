package com.shah.bookstoreapi.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shah.bookstoreapi.model.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class BookRepositoryTest {

    private final BookRepository repository;

    BookRepositoryTest(BookRepository repository) {
        this.repository = repository;
    }

    @Test
    void findByTitle() {
        List<Book> byTitle = repository.findByTitleIgnoreCase("Ghostbusters");
        if (!byTitle.isEmpty()) {
            log.info("Book found: {}", byTitle);
        } else
            log.info("Book not found");
        assertThat(byTitle).isNotEmpty();
    }

    @Test
    void findByAuthor() {
        List<Book> byAuthor = repository.findByAuthorNameIgnoreCase("John");
        if (!byAuthor.isEmpty()) {
            log.info("Book found: {}", byAuthor);
        } else
            log.info("Book not found");
        assertThat(byAuthor).isNotEmpty();
    }

    @Test
    void findByTitleAndAuthorName() {
        List<Book> byTitleAndAuthor = repository.findByTitleIgnoreCaseAndAuthorNameIgnoreCase("Stock trading", "Adam");
        if (!byTitleAndAuthor.isEmpty()) {
            log.info("Book found: {}", byTitleAndAuthor);
        } else
            log.info("Book not found");
        assertThat(byTitleAndAuthor).isNotEmpty();
    }

    @Test
    void checkForPreLoadedData() throws JsonProcessingException {
        List<Book> preloadedBooks = repository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        System.out.println(objectMapper.writeValueAsString(preloadedBooks));
        assertThat(preloadedBooks).hasSizeGreaterThan(1);
    }
}