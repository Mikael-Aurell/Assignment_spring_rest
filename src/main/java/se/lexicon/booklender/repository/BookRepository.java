package se.lexicon.booklender.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.booklender.entity.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
}
