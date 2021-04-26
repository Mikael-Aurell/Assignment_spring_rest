package se.lexicon.booklender.service;

import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.exception.DataNotFoundException;

import java.util.List;

public interface LibraryUserService {
    LibraryUserDto findById(int userId) throws DataNotFoundException;
    LibraryUserDto findByEmail(String email);

    LibraryUserDto create(LibraryUserDto dto);
    LibraryUserDto update(LibraryUserDto dto) throws DataNotFoundException;

    List<LibraryUserDto> findAll();
    boolean delete(int userId) throws DataNotFoundException;
}
