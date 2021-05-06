package se.lexicon.booklender.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class BookDto {
    private int bookId;
    //@Size(min = 2, max = 100, message = "The Length for title is 2-100.")
    private String title;
    private boolean available;
    private boolean reserved;
    //@Size(min = 1, max = 31, message = "Max Loan Days should be in 1-31 days.")
    private int maxLoanDays;
    private BigDecimal finePerDay;
    private String description;
}
