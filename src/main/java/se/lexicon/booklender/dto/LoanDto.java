package se.lexicon.booklender.dto;

import lombok.Data;
import se.lexicon.booklender.entity.Book;
import se.lexicon.booklender.entity.LibraryUser;

import java.time.LocalDate;

@Data
public class LoanDto {
    private long loanId;
    private LibraryUserDto loanTakerDto; //todo: change to loanTaker to fetch automatic from db
    private BookDto bookDto; //todo: change to book to fetch automatic from db
    private LocalDate loanDate;
    private boolean terminated;
}
