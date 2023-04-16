package com.shah.bookstoreapi.controller;

import com.shah.bookstoreapi.impl.BookServiceImpl;
import com.shah.bookstoreapi.model.entity.Author;
import com.shah.bookstoreapi.model.entity.Book;
import com.shah.bookstoreapi.model.request.CreateBookRequest;
import com.shah.bookstoreapi.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;

import static com.shah.bookstoreapi.model.Constants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private BookServiceImpl service;

    @Autowired
    private BookRepository repository;
    private CreateBookRequest createBookRequest;
    private Author author1;
    private Author author2;

    private Book book1;
    private Book savedBook;
    ObjectMapper objectMapper = new ObjectMapper();

    /** TODO
     * Add test case for ControllerAdvice
     * https://reflectoring.io/spring-boot-web-controller-test/
     **/

    @BeforeEach
    public void setUp() throws Exception {
        author1 = Author.builder()
                .name("John")
                .birthday(LocalDate.of(1985, 8, 30))
                .build();
        author2 = Author.builder()
                .name("Bob")
                .birthday(LocalDate.of(1970, 8, 30))
                .build();
        book1 = Book.builder()
                .author(Arrays.asList(author1, author2))
                .title("Test Book")
                .genre("Horror")
                .year(Year.of(2000))
                .price(BigDecimal.valueOf(25.50))
                .build();

        savedBook = repository.save(book1);


        createBookRequest = CreateBookRequest.builder()
                .author(Arrays.asList(author1, author2))
                .title("Ghostbusters")
                .genre("Horror")
                .year(Year.of(2000))
                .price(BigDecimal.valueOf(25.50))
                .build();
        /**
         to fix the error ‘Java 8 date/time type not supported by default‘ while serializing and deserializing Java 8 Date time classes using Jackson.
         **/
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getBookByTitleAndOrAuthor_isUnauthorized() throws Exception {
        mockMvc.perform(get(API_V1 + GET_BOOK_BY_TITLE_AUTHOR))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser("USER")
    @Test
    void getBookByTitleAndOrAuthor_authorized() throws Exception {
        mockMvc.perform(get(API_V1 + GET_BOOK_BY_TITLE_AUTHOR)
                        .param("title", "ghostbusters")
                        .param("author", "john"))
                .andDo(print())
                .andExpect(jsonPath("$.data.[0].title", Matchers.equalToIgnoringCase("ghostbusters")))
                .andExpect(status().isFound());
    }

    @WithMockUser("USER")
    @Test
    void addBook_authorized() throws Exception {
        mockMvc.perform(post(API_V1 + ADD_BOOK)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    /**
     * Check that other roles is forbidden to perform this operation.
     *
     * @throws Exception
     */
    @Test
    @WithMockUser("USER")
    void deleteBook_forbidden() throws Exception {
        mockMvc.perform(delete(API_V1 + DELETE_BOOK + "/" + savedBook.getIsbn()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    /**
     * Check that only ADMIN role can perform this operation.
     * @throws Exception
     */
    @WithMockUser(roles = {"ADMIN"})
    @Test
    void deleteBook_authorized() throws Exception {
        mockMvc.perform(delete(API_V1 + DELETE_BOOK + "/" + savedBook.getIsbn().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser("USER")
    @Test
    void updateBook() throws Exception {
        /**
         * Since updateBookRequest has exactly same field as savedBook, we will use savedBook as request body
         */
        savedBook.setTitle("Changed title");
        mockMvc.perform(put(API_V1 + UPDATE_BOOK)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedBook)))
                .andDo(print())
                .andExpect(jsonPath("$.data.title", Matchers.equalToIgnoringCase("Changed title")))
                .andExpect(status().isOk());
    }
}