package se.lexicon.booklender.dto;

import lombok.Data;
import se.lexicon.booklender.entity.Book;
import se.lexicon.booklender.entity.LibraryUser;

import java.time.LocalDate;

@Data
public class LoanDto {
    private long loanId;
    private LibraryUser loanTaker;
    private Book book;
    private LocalDate loanDate;
    private boolean terminated;
}
