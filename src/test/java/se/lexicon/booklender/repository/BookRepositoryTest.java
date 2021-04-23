package se.lexicon.booklender.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.booklender.entity.Book;
import se.lexicon.booklender.repository.BookRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BookRepositoryTest {

    BookRepository bookRepository;
    Book testBook;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @BeforeEach
    public void setup(){
        testBook = new Book();
        testBook.setTitle("How to Become a senor Java Fullstack Developer");
        testBook.setAvailable(true);
        testBook.setReserved(false);
        testBook.setMaxLoanDays(30);
        testBook.setFinePerDay(BigDecimal.valueOf(1 / 8));
        testBook.setDescription("Java");

        bookRepository.save(testBook);
    }

    @Test
    @DisplayName("Test1 Save & FindAll")
    public void test_save(){
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().iterator().forEachRemaining(bookList::add);
        Assertions.assertEquals(30,bookList.get(0).getMaxLoanDays());
    }

    @Test
    @DisplayName("Test2 FindById")
    public void test_find_by_id(){
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().iterator().forEachRemaining(bookList::add);
        Assertions.assertEquals(bookList.get(0).getBookId(), bookRepository.findById(1).get().getBookId());
    }

    @Test
    @DisplayName("Test3 Delete")
    public void test_delete(){
        bookRepository.delete(testBook);
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().iterator().forEachRemaining(bookList::add);
        Assertions.assertEquals(0, bookList.size());
    }
}
