package com.vinc.bookstoreservice.mapper;

import com.vinc.bookstoreservice.dto.AuthorDto;
import com.vinc.bookstoreservice.model.Author;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class AuthorMapper implements CoreMapper<AuthorDto, Author> {


    @Override
    public Author toEntity(AuthorDto dto) {
        Author author = new Author();
        if (StringUtils.hasText(dto.getId())) {
            author.setId(UUID.fromString(dto.getId()));
        }
        return getAuthor(dto, author);
    }

    @NonNull
    public static Author getAuthor(AuthorDto dto, Author author) {
        author.setName(dto.getName());
        author.setBorn(LocalDateTime.parse(dto.getBorn()+"T00:00:00", DateTimeFormatter.ISO_DATE_TIME));
        if (StringUtils.hasText(dto.getDod())) {
            author.setDod(LocalDateTime.parse(dto.getDod()+"T00:00:00", DateTimeFormatter.ISO_DATE_TIME));
        }
        author.setBiography(dto.getBiography());
        return author;
    }

    @Override
    public AuthorDto toMinimalDTO(Author entity) {
        AuthorDto dto = minimalInstance(AuthorDto.class, entity);
        dto.setName(entity.getName());
        dto.setBorn(entity.getBorn().format(DateTimeFormatter.ISO_DATE));
        return dto;
    }

    @Override
    public AuthorDto toDTO(Author entity) {
        AuthorDto dto = minimalInstance(AuthorDto.class, entity);
        dto.setName(entity.getName());
        dto.setBorn(entity.getBorn().format(DateTimeFormatter.ISO_DATE));
        dto.setDod(entity.getDod().format(DateTimeFormatter.ISO_DATE));
        dto.setBiography(entity.getBiography());
        dto.setCreated(entity.getCreated());
        dto.setModified(entity.getModified());
        return dto;
    }

}
