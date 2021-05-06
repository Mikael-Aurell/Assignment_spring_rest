package se.lexicon.booklender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import se.lexicon.booklender.dto.LibraryUserDto;
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

    @GetMapping("/")
    public ResponseEntity<List<LibraryUserDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(libraryUserService.findAll());
    }

    @GetMapping("/{id}")
        public ResponseEntity<LibraryUserDto> findById(@PathVariable("id")Integer userId){
        if(userId == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            try {
                return ResponseEntity.status(HttpStatus.OK).body(libraryUserService.findById(userId));
            } catch (DataNotFoundException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<LibraryUserDto> findByEmail(@PathVariable("email") String email){
        if(email.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.OK).body(libraryUserService.findByEmail(email));
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<LibraryUserDto> save(@RequestBody LibraryUserDto dto){
        if(dto == null) {
            if (dto.getUserId() <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryUserService.create(dto));
    }

    @Transactional
    @PutMapping("/")
    public ResponseEntity<LibraryUserDto> update(@RequestBody LibraryUserDto dto) throws DataNotFoundException {
        if(dto != null)
            if (dto.getUserId() < 1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            return ResponseEntity.status(HttpStatus.OK).body(libraryUserService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LibraryUserDto> delete(@PathVariable("id")Integer id) throws DataNotFoundException {
        if (id < 1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            libraryUserService.delete(id); //delete returns void
            return ResponseEntity.status(HttpStatus.OK).build();
    }

}
