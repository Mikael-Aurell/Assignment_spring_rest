package se.lexicon.booklender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.lexicon.booklender.dto.BookDto;
import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.exception.DataNotFoundException;
import se.lexicon.booklender.service.BookService;

import java.util.List;

@RestController
public class BookController {

    BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/v1/book")
    public ResponseEntity<List<BookDto>> findAll(){
        if (bookService.findAll().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.FOUND).body(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable("id")Integer bookId){
        if(bookId <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.findById(bookId));
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<BookDto> save(@RequestBody BookDto dto){
        if(dto == null)
            if (dto.getBookId() != 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(dto));
    }

    @Transactional
    @PutMapping("/")
    public ResponseEntity<BookDto> update(@RequestBody BookDto dto){
        if(dto != null)
            if (dto.getBookId() < 1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.update(dto));
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
