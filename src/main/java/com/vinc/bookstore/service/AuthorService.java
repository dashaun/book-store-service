package com.vinc.bookstore.service;

import com.vinc.bookstore.dto.AuthorDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {

    Flux<AuthorDto> getAllAuthors();

    Mono<AuthorDto> getAuthorById(String id);

    Mono<AuthorDto> createAuthor(AuthorDto author);

    Mono<AuthorDto> updateAuthor(String id, AuthorDto author);

    Mono<Void> deleteAuthor(String id);

    Mono<Integer> generateAuthors(Integer numberOfAuthors);

}
