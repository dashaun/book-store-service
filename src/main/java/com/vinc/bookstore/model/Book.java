package com.vinc.bookstore.model;

import com.vinc.bookstore.enums.Genre;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import static jakarta.persistence.EnumType.STRING;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@Table
public class Book extends CoreEntity {

    @NotBlank
    private String isbn;
    @NotBlank
    private String title;
    @NotNull
    @Enumerated(STRING)
    private Genre genre;
    @NotNull
    private Long authorId;


}
