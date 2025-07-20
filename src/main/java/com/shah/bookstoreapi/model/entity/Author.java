package com.shah.bookstoreapi.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author NORUL
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Author {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JsonIgnore
    private UUID authorId;
    @Schema(type = "string", example = "Tom Cruise")
    @Length(min = 3, message = "Author name must be minimum 3 character")
    private String name;

    @Schema(example = "20-12-1985",
            type = "string")
    @Past(message = "Birthday must be in the past!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthday;

}
