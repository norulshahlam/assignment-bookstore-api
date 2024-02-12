package com.shah.bookstoreapi.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.UUID;

/**
 * @author NORUL
 */
@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID isbn;

    private String title;
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(
            name = "isbn",
            referencedColumnName = "isbn")
    private List<Author> author;
    @PastOrPresent(message = "Year must be in the past or present")
    private Year year;
    private BigDecimal price;
    private String genre;

}
