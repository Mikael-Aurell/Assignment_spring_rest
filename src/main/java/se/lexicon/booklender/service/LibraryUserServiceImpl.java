package se.lexicon.booklender.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.entity.LibraryUser;
import se.lexicon.booklender.exception.DataNotFoundException;
import se.lexicon.booklender.repository.LibraryUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryUserServiceImpl implements LibraryUserService{

    LibraryUserRepository   libraryUserRepository;
    ModelMapper             modelMapper;

    @Autowired
    public void setLibraryUserRepository(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public LibraryUserDto findById(int userId) throws DataNotFoundException {
        if (userId == 0) throw new IllegalArgumentException("Id should not be empty");

        return modelMapper.map(libraryUserRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("LibraryUserDto not found.")), LibraryUserDto.class);
    }



    @Override
    public List<LibraryUserDto> findAll() {
        List<LibraryUser> libraryUserList = new ArrayList<>();
        libraryUserRepository.findAll().iterator().forEachRemaining(libraryUserList::add);
        return libraryUserList.stream().map(libraryUser -> modelMapper.map(libraryUser,LibraryUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public LibraryUserDto findByEmail(String email) {
        if (email == null) throw new IllegalArgumentException("Email should not be null");

        return modelMapper.map(libraryUserRepository.findLibraryUserByEmailIgnoreCase(email),LibraryUserDto.class);
    }

    @Override
    public LibraryUserDto create(LibraryUserDto dto) {
        if (dto == null) throw new IllegalArgumentException("The LibraryUserDto object not found");
        if (dto.getUserId() != 0) throw new IllegalArgumentException("The LibraryUserDto should be empty");

        return modelMapper.map(libraryUserRepository.save(modelMapper.map(dto, LibraryUser.class)),LibraryUserDto.class);
    }

    @Override
    public LibraryUserDto update(LibraryUserDto dto) throws DataNotFoundException {
        if (dto == null) throw new IllegalArgumentException("The dto object not found");
        if (dto.getUserId() < 1) throw new IllegalArgumentException("The LibraryUserDto is not valid");

        Optional<LibraryUser> bookOptional = libraryUserRepository.findById(modelMapper.map(dto,LibraryUser.class).getUserId());
        if (bookOptional.isPresent()){
            return modelMapper.map(libraryUserRepository.save(modelMapper.map(dto,LibraryUser.class)), LibraryUserDto.class);
        }
        else throw new DataNotFoundException("LibraryUserDto");
    }

    @Override
    public void delete(int userId) throws DataNotFoundException { //changed delete return void as repository
        if (userId < 1)throw new IllegalArgumentException("The id is not valid");

        libraryUserRepository.delete(modelMapper.map(libraryUserRepository.findById(userId)
                .orElseThrow(()->new DataNotFoundException("Id ")), LibraryUser.class));
    }
}
