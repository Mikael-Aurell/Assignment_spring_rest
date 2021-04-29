package se.lexicon.booklender.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.booklender.entity.Book;
import se.lexicon.booklender.entity.LibraryUser;
import se.lexicon.booklender.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoanRepositoryTest {

    Book testBook;
    LibraryUser testLibraryUser;
    Loan testObject;

    LoanRepository          loanRepository;
    BookRepository          bookRepository;
    LibraryUserRepository   libraryUserRepository;

    @Autowired
    public void setLoanRepository(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setLibraryUserRepository(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @BeforeEach
    void setUp() {

        testBook = new Book();
        testBook.setTitle("How to Become a senor Java Fullstack Developer");
        testBook.setAvailable(true);
        testBook.setReserved(false);
        testBook.setMaxLoanDays(30);
        testBook.setFinePerDay(BigDecimal.valueOf(1 / 8));
        testBook.setDescription("Java");

        bookRepository.save(testBook);

        testLibraryUser = new LibraryUser();
        testLibraryUser.setRegDate(LocalDate.of(1978, 4, 30));
        testLibraryUser.setName("Mikael Aurell");
        testLibraryUser.setEmail("aurell.mikael@gmail.com");

        libraryUserRepository.save(testLibraryUser);

        testObject = new Loan();
        testObject.setBook(testBook);
        testObject.setLoanTaker(testLibraryUser);
        testObject.setTerminated(true);
        testObject.setLoanDate(LocalDate.of(2021,4,22));

        loanRepository.save(testObject);
    }

    @Test
    public void test_findById(){
        List<Loan> loanList = new ArrayList<>();
        loanRepository.findAll().iterator().forEachRemaining(loanList::add);
        Long expectedId = loanList.get(0).getLoanId();
        Optional<Loan> actualId = loanRepository.findById(expectedId);
        if(actualId.isPresent()) {
            assertEquals(testLibraryUser.getName(), actualId.get().getLoanTaker().getName());
        }
    }

    @Test
    public void test_findAll (){
        List<Loan> loanList = new ArrayList<>();
        loanRepository.findAll().iterator().forEachRemaining(loanList::add);

        assertEquals(1, loanList.size());
    }

    @Test
    public void test_delete(){
        List<Loan> loanList = new ArrayList<>();
        loanRepository.delete(testObject);
        List<Loan> emptyList = new ArrayList<>();
        loanRepository.findAll().iterator().forEachRemaining(loanList::add);

        assertEquals(emptyList, loanList);
    }

    @Test
    public void test_find_loan_by_userId(){
        assertEquals("Mikael Aurell",loanRepository.findLoansByLoanTaker_UserId(1).get(0).getLoanTaker().getName());
    }

    @Test
    public void test_find_loan_by_bookId(){
        assertEquals("Java", loanRepository.findLoansByBook_BookId(1).get(0).getBook().getDescription());
    }

    @Test
    public void test_find_loan_by_terminated(){
        assertEquals(1, loanRepository.findLoansByTerminated(true).size());
    }
}
