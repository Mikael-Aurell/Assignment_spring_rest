package se.lexicon.booklender.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loanId;

    @ManyToOne
    @JoinColumn(name = "library_user_user_id")
    private LibraryUser loanTaker;

    @ManyToOne
    @JoinColumn(columnDefinition= "book_book_id")
    private Book book;

    @Column(nullable = false)
    private LocalDate loanDate;

    @Column(name = "_terminated")
    private boolean terminated;

}
