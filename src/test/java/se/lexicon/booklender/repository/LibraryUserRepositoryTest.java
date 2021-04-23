package se.lexicon.booklender.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.booklender.entity.LibraryUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LibraryUserRepositoryTest {

    LibraryUser libraryUser;
    LibraryUser libraryUser2;

    LibraryUserRepository libraryUserRepository;

    @Autowired
    public void setLibraryUserRepository(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @BeforeEach
    public void setUp() {

        libraryUser = new LibraryUser();
        libraryUser.setRegDate(LocalDate.now());
        libraryUser.setName("Test User 1");
        libraryUser.setEmail("test1@test.se");

        libraryUser2 = new LibraryUser();
        libraryUser2.setRegDate(LocalDate.now());
        libraryUser2.setName("Test User 2");
        libraryUser2.setEmail("test2@test.se");

        libraryUserRepository.save(libraryUser);
        libraryUserRepository.save(libraryUser2);
    }

    @Test
    public void test_findById() {
        List<LibraryUser> userList = new ArrayList<>();
        libraryUserRepository.findAll().iterator().forEachRemaining(userList::add);
        Integer expectedId = userList.get(1).getUserId();
        Optional<LibraryUser> actualId = libraryUserRepository.findById(expectedId);

        assertEquals("Test User 2", actualId.get().getName());
    }

    @Test
    public void test_findAll() {
        List<LibraryUser> userList = new ArrayList<>();
        libraryUserRepository.findAll().iterator().forEachRemaining(userList::add);

        assertEquals(2, userList.size());
    }

    @Test
    public void test_delete() {
        List<LibraryUser> userList = new ArrayList<>();
        libraryUserRepository.delete(libraryUser);
        libraryUserRepository.delete(libraryUser2);
        List<LibraryUser> emptyList = new ArrayList<>();
        libraryUserRepository.findAll().iterator().forEachRemaining(userList::add);

        assertEquals(emptyList, userList);
    }


}
