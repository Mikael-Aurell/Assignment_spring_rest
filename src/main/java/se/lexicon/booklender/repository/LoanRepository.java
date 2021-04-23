package se.lexicon.booklender.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.booklender.entity.Loan;

public interface LoanRepository extends CrudRepository<Loan, Long> {
}
