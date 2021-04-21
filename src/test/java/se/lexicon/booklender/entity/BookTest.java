package se.lexicon.booklender.entity;

import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class BookTest {
    Book testObject;

    @BeforeEach
    public void setup() {
        testObject = new Book();
        testObject.setTitle("How to Become a senor Java Fullstack Developer");
        testObject.setAvailable(true);
        testObject.setReserved(false);
        testObject.setMaxLoanDays(30);
        testObject.setFinePerDay(BigDecimal.valueOf(1 / 8));
        testObject.setDescription("Java");
    }

    @Test
    @DisplayName("Test 1 Create Book")
    public void test_create_book(){
        Assertions.assertEquals("Java", testObject.getDescription());
        Assertions.assertEquals(30, testObject.getMaxLoanDays());
        Assertions.assertEquals("How to Become a senor Java Fullstack Developer", testObject.getTitle());
    }

    @Test
    @DisplayName("Test2 Equal")
    public void test_equal(){

        Book actual = new Book();
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
        Book actual = new Book();
        actual.setTitle("How to Become a senor Java Fullstack Developer");
        actual.setAvailable(true);
        actual.setReserved(false);
        actual.setMaxLoanDays(30);
        actual.setFinePerDay(BigDecimal.valueOf(1 / 8));
        actual.setDescription("Java");

        Assertions.assertEquals(actual.hashCode(),testObject.hashCode());

    }
}