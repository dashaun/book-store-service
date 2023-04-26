package com.vinc.bookstore.service;

import com.vinc.bookstore.dto.AuthorDto;
import com.vinc.bookstore.mapper.AuthorMapper;
import com.vinc.bookstore.model.Author;
import com.vinc.bookstore.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthorServiceImplShould {

    private AuthorServiceImpl uut;
    private AuthorRepository authorRepository;

    @BeforeEach
    public void setUp() {
        authorRepository = mock(AuthorRepository.class);
        AuthorMapper authorMapper = new AuthorMapper();
        uut = new AuthorServiceImpl(authorRepository, authorMapper);
    }

    @Test
    void getAllAuthorsWhenEmptyRepository() {
        when(authorRepository.findAll()).thenReturn(Flux.empty());

        Flux<AuthorDto> allAuthors = uut.getAllAuthors();

        StepVerifier.create(allAuthors)
                .expectComplete()
                .verify();
    }

    @Test
    void getAllAuthors() {
        when(authorRepository.findAll()).thenReturn(Flux.fromIterable(generateAuthors(3)));

        Flux<AuthorDto> allAuthors = uut.getAllAuthors();

        StepVerifier.create(allAuthors)
                .assertNext(a -> {
                    assert StringUtils.hasText(a.getId());
                })
                .assertNext(a -> {
                    assert StringUtils.hasText(a.getId());
                })
                .assertNext(a -> {
                    assert StringUtils.hasText(a.getId());
                })
                .expectComplete()
                .verify();
    }


    private List<Author> generateAuthors(int numberOfAuthors) {
        List<Author> generatedAuthors = new ArrayList<>(numberOfAuthors);
        for (int authorNum = 1; authorNum <= numberOfAuthors; authorNum++) {
            Author author = new Author();
            author.setId(UUID.randomUUID());
            author.setName("Ernest Hemingway" + authorNum);
            author.setBorn(LocalDateTime.of(1850, 5, 7, 13, 45));
            author.setDod(LocalDateTime.of(1950, 5, 7, 13, 45));
            author.setBiography("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt "
                    + "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris "
                    + "nisi ut aliquip ex ea commodo consequat.");
            generatedAuthors.add(author);
        }
        ;
        return generatedAuthors;
    }
}