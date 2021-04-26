package se.lexicon.booklender.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.entity.LibraryUser;
import se.lexicon.booklender.exception.DataNotFoundException;
import se.lexicon.booklender.repository.LibraryUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        //Single Line
        return modelMapper.map(libraryUserRepository.findById(userId).
                        orElseThrow(() -> new DataNotFoundException("LibraryUserDto not found.")), LibraryUserDto.class);

        //Full classes
        /*if (libraryUserOptional.isPresent()){
            LibraryUserDto convertedData = modelMapper.map(libraryUserOptional, LibraryUserDto.class);
            return convertedData;
        } else {
            throw new DataNotFoundException("LibraryUserDto not found.");
        }*/
    }

    @Override
    public LibraryUserDto findByEmail(String email) {
        if (email == null) throw new IllegalArgumentException("Email should not be null");
        return modelMapper.map(libraryUserRepository.findLibraryUserByEmailIgnoreCase(email),LibraryUserDto.class);


    }

    @Override
    public LibraryUserDto create(LibraryUserDto dto) {
        if (dto != null) throw new IllegalArgumentException("The dto object not found");
        if (dto.getUserId() != 0) throw new IllegalArgumentException("The LibraryUserDto should be empty");

        LibraryUser libraryUserData = libraryUserRepository.save(modelMapper.map(dto, LibraryUser.class));
        return modelMapper.map(libraryUserData,LibraryUserDto.class);

    }

    @Override
    public LibraryUserDto update(LibraryUserDto dto) throws DataNotFoundException {
        if (dto != null) throw new IllegalArgumentException("The dto object not found");
        if (dto.getUserId() != 0) throw new IllegalArgumentException("The LibraryUserDto should be empty");
        Optional<LibraryUser> libraryUserFindData = libraryUserRepository.findById(dto.getUserId());
        if (libraryUserFindData.isPresent()){
            return modelMapper.map(
                    libraryUserRepository.save(modelMapper.map(libraryUserFindData, LibraryUser.class)),
                    LibraryUserDto.class);
        } else throw new DataNotFoundException("The LibraryUserDto is not found.");

    }

    @Override
    public List<LibraryUserDto> findAll() {
        List<LibraryUser> libraryUserList = new ArrayList<>();

        libraryUserRepository.findAll().iterator().forEachRemaining(libraryUserList::add);
        List<LibraryUserDto> libraryUserListDto= libraryUserList.stream().map(libraryUser -> modelMapper.map(libraryUser,LibraryUserDto.class))
                .collect(Collectors.toList());

        return libraryUserListDto;
    }

    @Override
    public boolean delete(int userId) throws DataNotFoundException {
        if (userId == 0) throw new IllegalArgumentException("Id should not be empty.");
        Optional<LibraryUser> libraryUserOptional = libraryUserRepository.findById(userId);
        if (libraryUserOptional.isPresent()) {
            libraryUserRepository.delete(modelMapper.map(libraryUserOptional, LibraryUser.class));
            return true;
        }
        else {
            throw new DataNotFoundException("The Id have no match in the database.");
        }

    }
}
