package com.vinc.bookstoreservice.service;

import com.vinc.bookstoreservice.AbstractIntegrationTest;
import com.vinc.bookstoreservice.dto.AuthorDto;
import com.vinc.bookstoreservice.mapper.AuthorMapper;
import com.vinc.bookstoreservice.model.Author;
import com.vinc.bookstoreservice.repository.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
class AuthorServiceImplIT extends AbstractIntegrationTest {

    @Autowired
    private AuthorServiceImpl uut;
    @Autowired
    AuthorMapper authorMapper;
    @Autowired
    private AuthorRepository authorRepository;

    @AfterEach
    public void cleanUp() {
        authorRepository.deleteAll().subscribe();
    }

    @Test
    void createAuthor() {
        Mono<AuthorDto> author = uut.createAuthor(generateAuthorDto());

        StepVerifier.create(author)
                .assertNext(a -> {
                    assert StringUtils.hasText(a.getId());
                })
                .expectComplete()
                .verify();
    }

    @Test
    void getAllAuthors() {
        authorRepository.saveAll(
                        IntStream.range(0, 3)
                                .mapToObj(i -> authorMapper.toEntity(generateAuthorDto()))
                                .collect(Collectors.toList()))
                .blockLast(Duration.ofMillis(300));

        Flux<AuthorDto> authors = uut.getAllAuthors();

        StepVerifier.create(authors)
                .expectSubscription()
                .thenRequest(Long.MAX_VALUE)
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }

    @Test
    void getAuthorById() {
        AtomicReference<String> authorId = new AtomicReference<>();
        Mono<Author> savedAuthor = authorRepository.save(authorMapper.toEntity(generateAuthorDto()));
        StepVerifier.create(savedAuthor)
                .assertNext(a -> {
                    assert StringUtils.hasText(a.getId().toString());
                    authorId.set(a.getId().toString());
                })
                .expectComplete()
                .verify();

        Mono<AuthorDto> author = uut.getAuthorById(authorId.get());

        StepVerifier.create(author)
                .assertNext(a -> {
                    assert StringUtils.hasText(a.getId());
                    assert a.getId().equals(authorId.get());
                })
                .expectComplete()
                .verify();
    }

    @Test
    void updateAuthor() {
        AtomicReference<String> authorId = new AtomicReference<>();
        String updatedName = "JJ";
        Mono<Author> savedAuthor = authorRepository.save(authorMapper.toEntity(generateAuthorDto()));
        StepVerifier.create(savedAuthor)
                .assertNext(a -> {
                    assert StringUtils.hasText(a.getId().toString());
                    authorId.set(a.getId().toString());
                })
                .expectComplete()
                .verify();
        AuthorDto authorDto = uut.getAuthorById(authorId.get()).block(Duration.ofMillis(500));
        assert authorDto != null;
        authorDto.setName(updatedName);

        Mono<AuthorDto> updatedAuthor = uut.updateAuthor(authorId.get(), authorDto);

        StepVerifier.create(updatedAuthor)
                .assertNext(a -> {
                    assert StringUtils.hasText(a.getId());
                    assert a.getId().equals(authorId.get());
                    assert a.getName().equals(updatedName);
                })
                .expectComplete()
                .verify();
    }

    @Test
    void deleteAuthor() {
        AtomicReference<String> authorId = new AtomicReference<>();
        Mono<Author> savedAuthor = authorRepository.save(authorMapper.toEntity(generateAuthorDto()));
        StepVerifier.create(savedAuthor)
                .assertNext(a -> {
                    assert StringUtils.hasText(a.getId().toString());
                    authorId.set(a.getId().toString());
                })
                .expectComplete()
                .verify();

        uut.deleteAuthor(authorId.get()).subscribe();

        Flux<AuthorDto> allAuthors = uut.getAllAuthors();
        StepVerifier.create(allAuthors)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    private AuthorDto generateAuthorDto() {
        return new AuthorDto("JP", "1899-07-21", "1961-07-02", "Ernest Miller Hemingway was an American novelist");
    }
}