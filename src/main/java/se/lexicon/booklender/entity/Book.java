package se.lexicon.booklender.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private boolean reserved;

    @Column(nullable = false)
    private int maxLoanDays;

    @Column(nullable = false)
    private BigDecimal finePerDay;

    @Column(nullable = false)
    private String description;
}
