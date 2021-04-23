package se.lexicon.booklender.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class LibraryUserTest {
    LibraryUser testObject;

    @BeforeEach
    public void setup(){
        testObject = new LibraryUser();
        testObject.setRegDate(LocalDate.of(1978, 04, 30));
        testObject.setName("Mikael Aurell");
        testObject.setEmail("aurell.mikael@gmail.com");
    }

    @Test
    @DisplayName("Test1 Create Library User")
    public void test_create_library_user(){
        Assertions.assertEquals("Mikael Aurell", testObject.getName());
        Assertions.assertEquals(30, testObject.getRegDate().getDayOfMonth());
    }

    @Test
    @DisplayName("Test2 Equal")
    public void test_equal(){
       LibraryUser actual = new LibraryUser();
        actual.setRegDate(LocalDate.of(1978, 04, 30));
        actual.setName("Mikael Aurell");
        actual.setEmail("aurell.mikael@gmail.com");
        Assertions.assertTrue(testObject.equals(actual));
    }

    @Test
    @DisplayName("Test Hash Code ")
    public void test_hash_code(){
        LibraryUser actual = new LibraryUser();
        actual.setRegDate(LocalDate.of(1978, 04, 30));
        actual.setName("Mikael Aurell");
        actual.setEmail("aurell.mikael@gmail.com");
        Assertions.assertEquals(actual.hashCode(),testObject.hashCode());
    }

}
