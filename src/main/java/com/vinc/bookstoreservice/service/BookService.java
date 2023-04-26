package com.vinc.bookstoreservice.service;

import com.vinc.bookstoreservice.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    Flux<Book> getAllBooks();

    Mono<Book> createBook(Book book);
//
//    Mono<Book> updateBook(Book book);
//
//    Mono<Void> deleteBook(String id);

}
