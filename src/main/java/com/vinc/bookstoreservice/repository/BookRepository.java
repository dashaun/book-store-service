package com.vinc.bookstoreservice.repository;

import com.vinc.bookstoreservice.model.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BookRepository extends ReactiveCrudRepository<Book, String> {

}
