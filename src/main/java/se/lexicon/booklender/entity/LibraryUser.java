package se.lexicon.booklender.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class LibraryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    //@Column(nullable = false)
    private LocalDate regDate;

    @Column(nullable = true)
    private String name;

    @Column(unique = true)
    private String email;
}
