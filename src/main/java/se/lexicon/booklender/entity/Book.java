package se.lexicon.booklender.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean available;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean reserved;

    @Column(nullable = false)
    private int maxLoanDays;

    @Column(nullable = false)
    private BigDecimal finePerDay;

    @Column(nullable = false)
    private String description;
}
