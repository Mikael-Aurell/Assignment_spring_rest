package se.lexicon.booklender.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class BookDto {
    private int bookId;
    @Size(min = 2, max = 100)
    private String title;
    private boolean available;
    private boolean reserved;
    @Size(min = 1, max = 31)
    private int maxLoanDays;
    private BigDecimal finePerDay;
    private String description;
}
