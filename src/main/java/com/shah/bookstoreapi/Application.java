package com.shah.bookstoreapi;

import com.shah.bookstoreapi.model.entity.Author;
import com.shah.bookstoreapi.model.entity.Book;
import com.shah.bookstoreapi.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;


/**
 * @author NORUL
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class Application implements CommandLineRunner {

	private BookRepository repository;

	public Application(BookRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Author author1 = Author.builder()
				.name("John")
				.birthday(LocalDate.of(1985, 8, 30))
				.build();
		Author author2 = Author.builder()
				.name("Bob")
				.birthday(LocalDate.of(1970, 8, 30))
				.build();
		Author author3 = Author.builder()
				.name("Adam")
				.birthday(LocalDate.of(1971, 8, 30))
				.build();
		Author author4 = Author.builder()
				.name("Peter")
				.birthday(LocalDate.of(1995, 8, 30))
				.build();

		Book book1 = Book.builder()
				.author(Arrays.asList(author1, author2))
				.title("Ghostbusters")
				.genre("Horror")
				.year(Year.of(2000))
				.price(BigDecimal.valueOf(25.50))
				.build();

		Book book2 = Book.builder()
				.author(Arrays.asList(author4, author3))
				.title("Stock trading")
				.genre("Finance")
				.year(Year.of(2000))
				.price(BigDecimal.valueOf(75.20))
				.build();

		repository.saveAll(Arrays.asList(book1,book2));
	}
}

