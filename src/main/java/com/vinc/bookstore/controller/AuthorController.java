package com.vinc.bookstore.controller;

import com.vinc.bookstore.dto.AuthorDto;
import com.vinc.bookstore.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public Flux<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Mono<AuthorDto> getAuthorById(@PathVariable("id") String id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public Mono<AuthorDto> createAuthor(@Valid @RequestBody AuthorDto author) {
        return authorService.createAuthor(author);
    }

    @PutMapping("/{id}")
    public Mono<AuthorDto> updateAuthor(@PathVariable("id") String id, @Valid @RequestBody AuthorDto author) {
        return authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAuthor(@PathVariable("id") String id) {
        return authorService.deleteAuthor(id);
    }

    @PostMapping("/generate/{number}")
    public Mono<Integer> generateAuthors(@PathVariable("number") Integer number) {
        return authorService.generateAuthors(number);
    }
}
