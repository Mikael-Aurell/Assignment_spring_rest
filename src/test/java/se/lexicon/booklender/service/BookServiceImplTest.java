package se.lexicon.booklender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.booklender.dto.BookDto;
import se.lexicon.booklender.exception.DataNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

@SpringBootTest
public class BookServiceImplTest {
    BookService testObject;
    BookDto testBookDto;
    BookDto testBook2Dto;

    @Autowired
    public void setTestObject(BookService testObject) {
        this.testObject = testObject;
    }

    @BeforeEach
    public void setup(){
        testBookDto = new BookDto();
        testBookDto.setTitle("How to Become a senor Java Fullstack Developer");
        testBookDto.setAvailable(false);
        testBookDto.setReserved(true);
        testBookDto.setMaxLoanDays(30);
        testBookDto.setFinePerDay(BigDecimal.valueOf(1 / 8));
        testBookDto.setDescription("Java");

        testBook2Dto = new BookDto();
        testBook2Dto.setTitle("How to Become a senor Java Fullstack Developer");
        testBook2Dto.setAvailable(true);
        testBook2Dto.setReserved(false);
        testBook2Dto.setMaxLoanDays(20);
        testBook2Dto.setFinePerDay(BigDecimal.valueOf(1 / 8));
        testBook2Dto.setDescription("C#");

        testObject.create(testBookDto);
    }

    @Test
    @DisplayName("Test_1: Find by Id")
    public void test_find_by_id(){
        try {
            assertEquals(1,testObject.findById(testBookDto.getBookId()));
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test_2: Create Book")
    public void test_create_book(){
        assertEquals("Java",testObject.create(testBookDto).getDescription());
    }

    @Test
    @DisplayName("Test_3: Update Book")
    public void test_update_book() {
        testBook2Dto.setBookId(1);

        try {
            assertEquals("C#", testObject.update(testBook2Dto).getDescription());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test_4: Delete")
    public void test_delete(){
        testObject.create(testBook2Dto);
        assertEquals(2,testObject.findAll().size());
        try {
            testObject.delete(2);  //void instead of returning boolean
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(1, testObject.findAll().size());
    }

    @Test
    @DisplayName("Test_5: FindAll")
    public void test_findAll(){
        testObject.create(testBook2Dto);
        assertEquals("Java", testObject.findAll().get(0).getDescription());
        assertEquals("C#", testObject.findAll().get(1).getDescription());
    }

    @Test
    @DisplayName("Test_6: Find By Title")
    public void test_find_by_title(){
        testObject.create(testBook2Dto);
        assertEquals(2,testObject.findByTitle("How to Become a senor Java Fullstack Developer").size());
    }

    @Test
    @DisplayName("Test_7: Find By Reserved")
    public void test_find_by_reserved(){
        assertEquals(testBookDto.getMaxLoanDays(),testObject.findByReserved(true).get(0).getMaxLoanDays());
    }

    @Test
    @DisplayName("Test_8: Find By Available")
    public void test_find_by_available(){
        testObject.create(testBook2Dto);
        assertEquals(testBook2Dto.getMaxLoanDays(),testObject.findByAvailable(true).get(0).getMaxLoanDays());
    }
}

