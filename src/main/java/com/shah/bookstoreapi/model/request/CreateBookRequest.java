package com.shah.bookstoreapi.model.request;

import com.shah.bookstoreapi.model.entity.Author;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

    @Schema(type = "string", example = "Adventures of Tintin")
    @Length(min = 3, message = "Title must be minimum 3 character")
    private String title;
    @NotEmpty(message = "Author information cannot be empty.")
    private List<@Valid Author> author;
    @Schema(type = "Year", example = "1990")
    @Past(message = "Year must be in the past!")
    private Year year;
    @Schema(example = "25.50")
    @Digits(integer = 4, fraction = 2, message = "Price must be in 2 decimal places")
    private BigDecimal price;
    @Schema(type = "string", example = "Horror")
    private String genre;
}
