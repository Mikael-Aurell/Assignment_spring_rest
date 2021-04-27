package se.lexicon.booklender.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class BookDtoTest {

    BookDto testObject;

    @BeforeEach
    public void setup() {
        testObject = new BookDto();
        testObject.setTitle("How to Become a senor Java Fullstack Developer");
        testObject.setAvailable(true);
        testObject.setReserved(false);
        testObject.setMaxLoanDays(30);
        testObject.setFinePerDay(BigDecimal.valueOf(1 / 8));
        testObject.setDescription("Java");
    }

        @Test
        @DisplayName("Test 1 Create BookDto")
        public void test_create_book_dto(){
            Assertions.assertEquals("Java", testObject.getDescription());
            Assertions.assertEquals(30, testObject.getMaxLoanDays());
            Assertions.assertEquals("How to Become a senor Java Fullstack Developer", testObject.getTitle());
        }

        @Test
        @DisplayName("Test2 Equal")
        public void test_equal(){
            BookDto actual = new BookDto();
            actual.setTitle("How to Become a senor Java Fullstack Developer");
            actual.setAvailable(true);
            actual.setReserved(false);
            actual.setMaxLoanDays(30);
            actual.setFinePerDay(BigDecimal.valueOf(1 / 8));
            actual.setDescription("Java");

            Assertions.assertTrue(testObject.equals(actual));
        }

        @Test
        @DisplayName("Test3 HashCode")
        public void test_hash_code(){
            BookDto actual = new BookDto();
            actual.setTitle("How to Become a senor Java Fullstack Developer");
            actual.setAvailable(true);
            actual.setReserved(false);
            actual.setMaxLoanDays(30);
            actual.setFinePerDay(BigDecimal.valueOf(1 / 8));
            actual.setDescription("Java");

            Assertions.assertEquals(actual.hashCode(),testObject.hashCode());

        }
}
