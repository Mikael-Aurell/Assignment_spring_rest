package se.lexicon.booklender.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.booklender.entity.LibraryUser;
import se.lexicon.booklender.entity.Loan;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {
    List<Loan> findLoansByLoanTaker_UserId(int userId);
    List<Loan> findLoansByBook_BookId(int bookId);
    List<Loan> findLoansByTerminated(boolean status);
}
