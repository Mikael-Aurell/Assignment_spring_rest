package se.lexicon.booklender.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.booklender.entity.Book;
import se.lexicon.booklender.entity.LibraryUser;
import se.lexicon.booklender.entity.Loan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoanRepositoryTest {

    Book book;
    LibraryUser user;
    Loan testObject;
    LoanRepository loanRepository;

    @Autowired
    public void setLoanRepository(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @BeforeEach
    void setUp() {

        testObject = new Loan();
        testObject.setLoanTaker(user);
        testObject.setBook(book);
        testObject.setTerminated(true);
        testObject.setLoanDate(LocalDate.of(1978,04,30));

        loanRepository.save(testObject);

    }

    @Test
    public void test_findById(){
        List<Loan> loanList = new ArrayList<>();
        loanRepository.findAll().iterator().forEachRemaining(loanList::add);
        Long expectedId = loanList.get(0).getLoanId();
        Optional<Loan> actualId = loanRepository.findById(expectedId);

        assertEquals(user, actualId.get().getLoanTaker());
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
}
