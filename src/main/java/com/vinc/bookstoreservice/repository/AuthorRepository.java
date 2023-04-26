package com.vinc.bookstoreservice.repository;

import com.vinc.bookstoreservice.model.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AuthorRepository extends ReactiveCrudRepository<Author, String> {

}
