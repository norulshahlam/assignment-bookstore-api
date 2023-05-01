package com.shah.bookstoreapi.model.response;

import com.shah.bookstoreapi.model.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.UUID;

/**
 * @author NORUL
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookResponse {
    private UUID isbn;
    private String title;
    private List<Author> author;
    private Year year;
    private BigDecimal price;
    private String genre;
}
