package se.lexicon.booklender.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class LibraryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false)
    private LocalDate regDate;

    @Column(nullable = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;
}
