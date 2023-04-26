package com.vinc.bookstoreservice.service;

import com.vinc.bookstoreservice.dto.AuthorDto;
import com.vinc.bookstoreservice.exceptions.AuthorNotFoundException;
import com.vinc.bookstoreservice.mapper.AuthorMapper;
import com.vinc.bookstoreservice.model.Author;
import com.vinc.bookstoreservice.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.IntStream;

import static com.vinc.bookstoreservice.mapper.AuthorMapper.getAuthor;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .map(authorMapper::toDTO);
    }

    @Override
    public Mono<AuthorDto> getAuthorById(String id) {
        UUID authorId;
        try {
            authorId = UUID.fromString(id);
        } catch (IllegalArgumentException exc) {
            return Mono.error(new AuthorNotFoundException(id));
        }
        return authorRepository.findById(authorId.toString())
                .switchIfEmpty(Mono.error(new AuthorNotFoundException(id)))
                .map(authorMapper::toDTO);
    }

    @Override
    public Mono<AuthorDto> createAuthor(AuthorDto author) {
        return authorRepository.save(authorMapper.toEntity(author))
                .map(authorMapper::toDTO);
    }

    @Override
    public Mono<AuthorDto> updateAuthor(String id, AuthorDto author) {
        UUID authorId;
        try {
            authorId = UUID.fromString(id);
        } catch (IllegalArgumentException exc) {
            return Mono.error(new AuthorNotFoundException(id));
        }
        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(new AuthorNotFoundException(id)))
                .flatMap(a -> {
                    a.setId(authorId);
                    return authorRepository.save(getAuthor(author, a))
                            .map(authorMapper::toDTO);
                });
    }

    @Override
    public Mono<Void> deleteAuthor(String id) {
        UUID authorId;
        try {
            authorId = UUID.fromString(id);
        } catch (IllegalArgumentException exc) {
            return Mono.error(new AuthorNotFoundException(id));
        }
        return authorRepository.deleteById(authorId.toString());
    }

    @Override
    public Mono<Integer> generateAuthors(Integer numberOfAuthors) {
        IntStream.range(0, numberOfAuthors).forEach(n -> {
            Author author = new Author();
            author.setName("Ernest Hemingway");
            author.setBorn(LocalDateTime.of(1850, 5, 7, 13, 45));
            author.setDod(LocalDateTime.of(1950, 5, 7, 13, 45));
            author.setBiography("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt "
                    + "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris "
                    + "nisi ut aliquip ex ea commodo consequat.");
            authorRepository.save(author).subscribe();
        });
        return Mono.just(numberOfAuthors);
    }

}
