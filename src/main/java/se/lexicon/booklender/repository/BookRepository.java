package se.lexicon.booklender.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.booklender.entity.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findBooksByReserved(boolean reserved);
    List<Book> findBooksByAvailable(boolean available);
    List<Book> findBooksByTitle(String title);
}
