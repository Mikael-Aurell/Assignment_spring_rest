package se.lexicon.booklender.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class LoanTest {

    Loan testObject;
    Book testBook;
    LibraryUser testLoanTaker;

    @BeforeEach
    public void setup() {
        testBook = new Book();
        testBook.setTitle("How to Become a senor Java Fullstack Developer");
        testBook.setAvailable(true);
        testBook.setReserved(false);
        testBook.setMaxLoanDays(30);
        testBook.setFinePerDay(BigDecimal.valueOf(1 / 8));
        testBook.setDescription("Java");

        testLoanTaker = new LibraryUser();
        testLoanTaker.setRegDate(LocalDate.of(1978, 04, 30));
        testLoanTaker.setName("Mikael Aurell");
        testLoanTaker.setEmail("aurell.mikael@gmail.com");

        testObject = new Loan();
        testObject.setBook(testBook);
        testObject.setLoanTaker(testLoanTaker);
        testObject.setLoanDate(LocalDate.of(2021,04,22));
    }

    @Test
    @DisplayName("Test1 Create Loan")
    public void test_create_loan(){
        Assertions.assertEquals(1978,testObject.getLoanTaker().getRegDate().getYear());
        Assertions.assertEquals("Java", testObject.getBook().getDescription());
        Assertions.assertEquals(22, testObject.getLoanDate().getDayOfMonth());
    }

    @Test
    @DisplayName("Test2 Equal")
    public void test_equal(){
        Loan actual = new Loan();
        actual.setBook(testBook);
        actual.setLoanTaker(testLoanTaker);
        actual.setLoanDate(LocalDate.of(2021,04,22));

        Assertions.assertTrue(testObject.equals(actual));
    }

    @Test
    @DisplayName("Test2 Hash Code")
    public void test_hash_code(){
        Loan actual = new Loan();
        actual.setBook(testBook);
        actual.setLoanTaker(testLoanTaker);
        actual.setLoanDate(LocalDate.of(2021,04,22));

        Assertions.assertEquals(actual.hashCode(),testObject.hashCode());
    }
}
