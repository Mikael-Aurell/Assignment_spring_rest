package se.lexicon.booklender.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class LibraryUserDto {
    @Column(nullable = false)
    private int userId;
    private LocalDate regDate;
    private String name;
    private String email;
}
