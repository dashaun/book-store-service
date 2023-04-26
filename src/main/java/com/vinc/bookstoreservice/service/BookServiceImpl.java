package com.vinc.bookstoreservice.service;

import com.vinc.bookstoreservice.model.Book;
import com.vinc.bookstoreservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    @Override
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Mono<Book> createBook(Book book) {
        return bookRepository.save(book);
    }
}
