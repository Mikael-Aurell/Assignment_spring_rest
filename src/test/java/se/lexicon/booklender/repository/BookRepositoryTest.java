package se.lexicon.booklender.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.booklender.entity.Book;
import se.lexicon.booklender.repository.BookRepository;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BookRepositoryTest {

    BookRepository bookRepository;
    Book testBook;
    Book testBook2;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @BeforeEach
    public void setup(){
        testBook = new Book();
        testBook.setTitle("How to Become a senor Fullstack Developer");
        testBook.setAvailable(true);
        testBook.setReserved(false);
        testBook.setMaxLoanDays(30);
        testBook.setFinePerDay(BigDecimal.valueOf(1 / 8));
        testBook.setDescription("Java");

        testBook2 = new Book();
        testBook2.setTitle("How to Become a senor Fullstack Developer");
        testBook2.setAvailable(true);
        testBook2.setReserved(true);
        testBook2.setMaxLoanDays(30);
        testBook2.setFinePerDay(BigDecimal.valueOf(1 / 8));
        testBook2.setDescription("C#");

        bookRepository.save(testBook);
        bookRepository.save(testBook2);
    }

    @Test
    @DisplayName("Test1 Save & FindAll")
    public void test_save(){
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().iterator().forEachRemaining(bookList::add);
        assertEquals(30,bookList.get(0).getMaxLoanDays());
    }

    @Test
    @DisplayName("Test2 FindById")
    public void test_find_by_id(){
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().iterator().forEachRemaining(bookList::add);
        assertEquals(bookList.get(0).getBookId(), bookRepository.findById(1).get().getBookId());
    }

    @Test
    @DisplayName("Test3 Delete")
    public void test_delete(){
        bookRepository.delete(testBook);
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().iterator().forEachRemaining(bookList::add);
        assertEquals(0, bookList.size());
    }

    @Test
    @DisplayName("Test4 Find By Reserved Status")
    public void test_find_by_reserved_status(){
        List<Book> actual = bookRepository.findBooksByReserved(true);
        assertEquals("How to Become a senor Fullstack Developer", actual.get(0).getTitle());
        testBook.setReserved(true);
        bookRepository.save(testBook);
        actual = bookRepository.findBooksByReserved(true);
        assertEquals("How to Become a senor Fullstack Developer", actual.get(1).getTitle());
    }

    @Test
    @DisplayName("Test5 Find By Available Status")
    public void test_find_by_available_status(){
        assertEquals("C#", bookRepository.findBooksByAvailable(true).get(1).getDescription());
    }

    @Test
    @DisplayName("Test6 Find By Title")
    public void test_find_by_title(){
        assertEquals("Java", bookRepository
                .findBooksByTitle("How to Become a senor Fullstack Developer").get(0).getDescription());
        assertEquals("C#", bookRepository
                .findBooksByTitle("How to Become a senor Fullstack Developer").get(1).getDescription());
    }

}
