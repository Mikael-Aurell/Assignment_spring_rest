package se.lexicon.booklender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.booklender.dto.BookDto;
import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.dto.LoanDto;
import se.lexicon.booklender.exception.DataNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class LoanServiceImplTest {

    BookDto testBookDto;
    LibraryUserDto testLoanTakerDto;
    LoanDto testLoanDto;
    LoanDto testLoan2Dto;

    LoanService testObject;
    BookService testBookService;
    LibraryUserService testLibraryUserService;

    @Autowired
    public void setTestObject(LoanService testObject) {
        this.testObject = testObject;
    }

    @Autowired
    public void setTestBookService(BookService testBookService) {
        this.testBookService = testBookService;
    }

    @Autowired
    public void setTestLibraryUserService(LibraryUserService testLibraryUserService) {
        this.testLibraryUserService = testLibraryUserService;
    }

    @BeforeEach
    public void setup(){
        testBookDto = new BookDto();
        testBookDto.setTitle("How to Become a senor Java Fullstack Developer");
        testBookDto.setAvailable(true);
        testBookDto.setReserved(false);
        testBookDto.setMaxLoanDays(30);
        testBookDto.setFinePerDay(BigDecimal.valueOf(1 / 8));
        testBookDto.setDescription("Java");

        testBookService.create(testBookDto);

        testLoanTakerDto = new LibraryUserDto();
        testLoanTakerDto.setRegDate(LocalDate.of(1978, 4, 30));
        testLoanTakerDto.setName("Mikael Aurell");
        testLoanTakerDto.setEmail("aurell.mikael@gmail.com");

        testLibraryUserService.create(testLoanTakerDto);

        testLoanDto = new LoanDto();
        testLoanDto.setBookDto(testBookService.findAll().get(0));
        testLoanDto.setLoanTakerDto(testLibraryUserService.findAll().get(0));
        testLoanDto.setLoanDate(LocalDate.of(2021,4,22));
        testLoanDto.setTerminated(false);

        testObject.create(testLoanDto);

        testLoan2Dto = new LoanDto();
        testLoan2Dto.setBookDto(testBookService.findAll().get(0));
        testLoan2Dto.setLoanTakerDto(testLibraryUserService.findAll().get(0));
        testLoan2Dto.setLoanDate(LocalDate.of(2021,4,22));
        testLoanDto.setTerminated(true);

    }

    @Test
    @DisplayName("Test_1: Find By Id")
    public void test_find_by_id(){
        try {
            assertEquals(1,testObject.findById(1).getLoanId());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test_2: Create")
    public void test_create(){
        testObject.create(testLoan2Dto);
        try {
            assertEquals(2,testObject.findById(2).getLoanId());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test_3: Update")
    public void test_update(){
        testLoanDto.setTerminated(true);
        try {
            testObject.update(testLoanDto);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test_4: Delete")
    public void test_delete(){
        testObject.create(testLoan2Dto);
        assertEquals(2,testObject.findAll().size());
        try {
            testObject.delete(testObject.findAll().get(1).getLoanId());
            assertEquals(1,testObject.findAll().size());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test_5: FindAll")
    public void test_findAll(){
        assertEquals(1,testObject.findAll().size());
        testObject.create(testLoan2Dto);
        assertEquals("Java",testObject.findAll().get(1).getBookDto().getDescription());
    }


    @Test
    @DisplayName("Test_6: Find Book By Id")
    public void test_find_by_book_id(){
        testObject.create(testLoan2Dto);
        assertEquals("Java",testObject.findByBookId(1).get(0).getBookDto().getDescription());
        //assertEquals("Java",testObject.findByBookId(1).get(0).getBookDto().getDescription());
    }
    @Test
    @DisplayName("Test_6: Find User By Id")
    public void test_find_by_user_id(){
        assertEquals("Mikael Aurell",testObject.findByUserId(1)
                .get(0).getLoanTakerDto().getName());
    }
    @Test
    @DisplayName("Test_6: Find By Terminated")
    public void test_find_by_terminated(){
        assertEquals(4,testObject.findByTerminated(false).get(0).getLoanDate().getMonth().getValue());
    }
}
