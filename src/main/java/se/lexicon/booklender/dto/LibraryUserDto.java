package se.lexicon.booklender.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class LibraryUserDto {
    private int userId;
    /*@JsonFormat(pattern = "yyyy-MM-dd")*/
    private LocalDate regDate;
    private String name;
    private String email;
}
