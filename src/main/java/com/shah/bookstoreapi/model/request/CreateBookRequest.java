package com.shah.bookstoreapi.model.request;

import com.shah.bookstoreapi.model.entity.Author;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;


/**
 * @author NORUL
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

    @Schema(type = "string", example = "Adventures of Tintin")
    @Length(min = 3, message = "Title must be minimum 3 character")
    @NotBlank(message = "title cannot be blank")
    private String title;

    @NotEmpty(message = "Author information cannot be empty.")
    private List<@Valid Author> author;

    @Schema(type = "Year", example = "1990")
    @Min(value = 1900, message = "year must be at least 1900")
    @Digits(fraction = 0, integer = 4, message = "year must be in yyyy format")
    @Max(value = 2222, message = "year must be less than 2222")
    @NotBlank(message = "year cannot be blank")
    private String year;

    @Schema(example = "25.50")
    @Digits(integer = 4, fraction = 2, message = "Price must be in 2 decimal places")
    @Positive(message="price must be positive")
    @NotBlank(message = "price cannot be blank")
    private String price;

    @Schema(type = "string", example = "Horror")
    @NotBlank(message = "genre cannot be blank")
    private String genre;
}
