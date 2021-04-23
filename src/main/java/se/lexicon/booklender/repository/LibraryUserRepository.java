package se.lexicon.booklender.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.booklender.entity.LibraryUser;

public interface LibraryUserRepository extends CrudRepository<LibraryUser, Integer> {
    LibraryUser findLibraryUserByEmailIgnoreCase(String email);
}
