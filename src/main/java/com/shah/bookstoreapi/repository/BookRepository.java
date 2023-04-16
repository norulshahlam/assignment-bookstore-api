package com.shah.bookstoreapi.repository;

import com.shah.bookstoreapi.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository  extends JpaRepository<Book, UUID> {
    List<Book> findByTitleIgnoreCase(String title);
    List<Book> findByAuthorNameIgnoreCase(String author);
    List<Book> findByTitleIgnoreCaseAndAuthorNameIgnoreCase(String title, String author);
}
