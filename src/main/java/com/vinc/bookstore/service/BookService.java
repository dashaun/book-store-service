package com.vinc.bookstore.service;

import com.vinc.bookstore.model.Book;
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
