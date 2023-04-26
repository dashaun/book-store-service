package com.vinc.bookstore.repository;

import com.vinc.bookstore.model.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AuthorRepository extends ReactiveCrudRepository<Author, String> {

}
