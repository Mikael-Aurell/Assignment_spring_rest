package se.lexicon.booklender.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.booklender.dto.BookDto;
import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.dto.LoanDto;
import se.lexicon.booklender.entity.Loan;
import se.lexicon.booklender.exception.DataNotFoundException;
import se.lexicon.booklender.repository.BookRepository;
import se.lexicon.booklender.repository.LibraryUserRepository;
import se.lexicon.booklender.repository.LoanRepository;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LoanServiceImpl implements LoanService{

    LibraryUserRepository libraryUserRepository;
    LoanRepository loanRepository;
    BookRepository bookRepository;
    ModelMapper modelMapper;

    @Autowired
    public void setLibraryUserRepository(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @Autowired
    public void setLoanRepository(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public LoanDto findById(long loanId) throws DataNotFoundException {
        if (loanId == 0) throw new IllegalArgumentException("Id should not be empty");

        return modelMapper.map(loanRepository.findById(loanId)
                .orElseThrow(() -> new DataNotFoundException("LoanDto not found.")), LoanDto.class);
    }

    @Override
    public LoanDto create(LoanDto dto) {
        if(dto == null) throw new IllegalArgumentException("BookDto not found");
        if(dto.getLoanId() != 0) throw new IllegalArgumentException("Id should be empty");

        return modelMapper.map(loanRepository.save(modelMapper.map(dto, Loan.class)),LoanDto.class);
    }

    @Override
    public LoanDto update(LoanDto dto) throws DataNotFoundException {
        if (dto == null) throw new IllegalArgumentException("The dto object not found");
        if (dto.getLoanId() < 1) throw new IllegalArgumentException("The BookDto is not valid");

        return modelMapper.map(loanRepository.save(loanRepository.findById(modelMapper.map(dto,Loan.class).getLoanId())
                .orElseThrow(()-> new DataNotFoundException("LoanDto "))),LoanDto.class);
    }

    @Override
    public List<LoanDto> findAll() {
        List<Loan> loanList = new ArrayList<>();
        loanRepository.findAll().iterator().forEachRemaining(loanList::add);

        List<LoanDto> loanDtoList = loanList.stream().map(loan -> modelMapper.map(loan,LoanDto.class)).collect(Collectors.toList());

        IntStream.range(0, loanDtoList.size()).forEach(x -> {
            loanDtoList.get(x).setLoanTakerDto(modelMapper.map(loanList.get(x).getLoanTaker(), LibraryUserDto.class));
            loanDtoList.get(x).setBookDto(modelMapper.map(loanList.get(x).getBook(), BookDto.class));
        });

        return loanDtoList;
    }

    @Override
    public List<LoanDto> findByBookId(int bookId) {
        if(bookId <1 )throw new IllegalArgumentException("The field is empty");

        List<Loan> loanList = new ArrayList<>();
        loanRepository.findLoansByBook_BookId(bookId).iterator().forEachRemaining(loanList::add);

        List<LoanDto> loanDtoList = loanRepository.findLoansByBook_BookId(bookId)
                .stream().map(book->modelMapper.map(book,LoanDto.class)).collect(Collectors.toList());


        IntStream.range(0, loanDtoList.size()).forEach(y -> {
            loanDtoList.get(y).setLoanTakerDto(modelMapper.map(loanList.get(y).getLoanTaker(), LibraryUserDto.class));
            loanDtoList.get(y).setBookDto(modelMapper.map(loanList.get(y).getBook(), BookDto.class));
        });

        return loanDtoList;
    }

    @Override
    public List<LoanDto> findByUserId(int userId) {
        if(userId <1 )throw new IllegalArgumentException("The field is empty");

        List<Loan> loanList = new ArrayList<>();
        loanRepository.findLoansByBook_BookId(userId).iterator().forEachRemaining(loanList::add);

        List<LoanDto> loanDtoList = loanRepository.findLoansByBook_BookId(userId)
                .stream().map(book->modelMapper.map(book,LoanDto.class)).collect(Collectors.toList());


        IntStream.range(0, loanDtoList.size()).forEach(y -> {
            loanDtoList.get(y).setLoanTakerDto(modelMapper.map(loanList.get(y).getLoanTaker(), LibraryUserDto.class));
            loanDtoList.get(y).setBookDto(modelMapper.map(loanList.get(y).getBook(), BookDto.class));
        });

        return loanDtoList;
    }

    @Override
    public List<LoanDto> findByTerminated(boolean terminated) {

        return loanRepository.findLoansByTerminated(terminated)
                .stream().map(book->modelMapper.map(book,LoanDto.class)).collect(Collectors.toList());
    }

    @Override
    public void delete(long loanId) throws DataNotFoundException {
        if (loanId <1)throw new IllegalArgumentException("The id is not valid");

        loanRepository.delete(modelMapper.map(loanRepository.findById(loanId)
                .orElseThrow(()->new DataNotFoundException("Id ")), Loan.class));
    }
}
