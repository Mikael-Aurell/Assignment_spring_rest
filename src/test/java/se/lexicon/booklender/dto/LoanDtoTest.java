package se.lexicon.booklender.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.booklender.entity.Book;
import se.lexicon.booklender.entity.LibraryUser;
import se.lexicon.booklender.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class LoanDtoTest {

    LoanDto testObject;

    BookDto testBookDto;
    LibraryUserDto testLoanTakerDto;

    @BeforeEach
    public void setup() {
        testBookDto = new BookDto();
        testBookDto.setTitle("How to Become a senor Java Fullstack Developer");
        testBookDto.setAvailable(true);
        testBookDto.setReserved(false);
        testBookDto.setMaxLoanDays(30);
        testBookDto.setFinePerDay(BigDecimal.valueOf(1 / 8));
        testBookDto.setDescription("Java");

        testLoanTakerDto = new LibraryUserDto();
        testLoanTakerDto.setRegDate(LocalDate.of(1978, 04, 30));
        testLoanTakerDto.setName("Mikael Aurell");
        testLoanTakerDto.setEmail("aurell.mikael@gmail.com");

        testObject = new LoanDto();
        testObject.setBookDto(testBookDto);
        testObject.setLoanTakerDto(testLoanTakerDto);
        testObject.setLoanDate(LocalDate.of(2021,04,22));
    }

    @Test
    @DisplayName("Test1 Create LoanDto")
    public void test_create_loan_dto(){
        Assertions.assertEquals(1978,testObject.getLoanTakerDto().getRegDate().getYear());
        Assertions.assertEquals("Java", testObject.getBookDto().getDescription());
        Assertions.assertEquals(22, testObject.getLoanDate().getDayOfMonth());
    }

    @Test
    @DisplayName("Test2 Equal")
    public void test_equal(){
        LoanDto actual = new LoanDto();
        actual.setBookDto(testBookDto);
        actual.setLoanTakerDto(testLoanTakerDto);
        actual.setLoanDate(LocalDate.of(2021,04,22));

        Assertions.assertTrue(testObject.equals(actual));
    }

    @Test
    @DisplayName("Test2 Hash Code")
    public void test_hash_code(){
        LoanDto actual = new LoanDto();
        actual.setBookDto(testBookDto);
        actual.setLoanTakerDto(testLoanTakerDto);
        actual.setLoanDate(LocalDate.of(2021,04,22));

        Assertions.assertEquals(actual.hashCode(),testObject.hashCode());
    }
}
