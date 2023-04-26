package com.vinc.bookstore.repository;

import com.vinc.bookstore.model.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BookRepository extends ReactiveCrudRepository<Book, String> {

}
