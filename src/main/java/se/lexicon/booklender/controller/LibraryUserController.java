package se.lexicon.booklender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.entity.LibraryUser;
import se.lexicon.booklender.exception.DataNotFoundException;
import se.lexicon.booklender.service.LibraryUserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/libraryUser")
public class LibraryUserController {

    //Dependency Injection
    LibraryUserService libraryUserService;

    @Autowired
    public void setLibraryUserService(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    private ResponseEntity<LibraryUserDto> badRequest(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    private ResponseEntity<LibraryUserDto> notFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<LibraryUserDto>> findAll(){
        return ResponseEntity.ok(libraryUserService.findAll());
    }

    @GetMapping("/{id}")
        public ResponseEntity<LibraryUserDto> findById(@PathVariable("id")Integer id){
        if(id < 1) return badRequest();
        try {
            return ResponseEntity.ok(libraryUserService.findById(id));
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return notFound();
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<LibraryUserDto> findByEmail(@PathVariable("email") String email){
        if(email == null) return badRequest();
        return ResponseEntity.ok(libraryUserService.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<LibraryUserDto> save(@RequestBody LibraryUserDto dto){
        if(dto == null)
            if (dto.getUserId() != 0) return badRequest();
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryUserService.create(dto));
    }

    @PutMapping("/")
    public ResponseEntity<LibraryUserDto> update(@RequestBody LibraryUserDto dto){
        if(dto == null)
            if (dto.getUserId() < 1) return badRequest();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(libraryUserService.update(dto));
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return notFound();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LibraryUserDto> delete(@PathVariable("id")Integer id){
        if (id < 1) return badRequest();
        try {
            libraryUserService.delete(id); //delete returns void
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return notFound();
        }
    }

}
