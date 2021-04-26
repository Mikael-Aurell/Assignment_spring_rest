package se.lexicon.booklender.dto;

import lombok.Data;
import se.lexicon.booklender.entity.Book;
import se.lexicon.booklender.entity.LibraryUser;

import java.time.LocalDate;

@Data
public class LoanDto {
    private long loanId;
    private LibraryUserDto loanTakerDto;
    private BookDto bookDto;
    private LocalDate loanDate;
    private boolean terminated;
}
