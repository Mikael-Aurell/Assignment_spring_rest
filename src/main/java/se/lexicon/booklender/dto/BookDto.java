package se.lexicon.booklender.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class BookDto {
    private int bookId;

    @NotNull
    @Size(min = 2, max = 100, message = "The Length for title is 2-100.")
    private String title;

    @NotNull
    private boolean available;

    @NotNull
    private boolean reserved;

    /*@NotEmpty
    @Size(min = 5, max = 31, message = "Max Loan Days should be in 1-31 days.")*/
    private int maxLoanDays;


    private BigDecimal finePerDay;

    @NotNull
    @Size(min = 2, max = 200, message = "The Length for description is 2-200.")
    private String description;
}
