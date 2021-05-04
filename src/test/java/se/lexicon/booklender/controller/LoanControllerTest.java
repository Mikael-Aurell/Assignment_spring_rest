package se.lexicon.booklender.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import se.lexicon.booklender.dto.BookDto;
import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.dto.LoanDto;
import se.lexicon.booklender.service.BookService;
import se.lexicon.booklender.service.LibraryUserService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerTest {

    LoanDto testObject;

    @Autowired
    MockMvc mockMvc;

    BookDto bookDto;
    LibraryUserDto loanTakerDto;
    ObjectMapper objectMapper;

    LibraryUserService testLibraryUserService;
    BookService testBookService;

    @Autowired
    public void setLibraryUserService(LibraryUserService libraryUserService) {
        this.testLibraryUserService = libraryUserService;
    }

    @Autowired
    public void setTestBookService(BookService testBookService) {
        this.testBookService = testBookService;
    }

    @BeforeEach
    public void setup() throws Exception {
        objectMapper = new ObjectMapper();

        bookDto = new BookDto();
        bookDto.setTitle("How to Become a senor Java Fullstack Developer");
        bookDto.setAvailable(true);
        bookDto.setReserved(false);
        bookDto.setMaxLoanDays(30);
        bookDto.setFinePerDay(BigDecimal.valueOf(1 / 8));
        bookDto.setDescription("Java");

        //Save BookDto
        String customerJsonMessage_BookDto = objectMapper.writeValueAsString(bookDto);
        System.out.println("customerJsonMessage = " + customerJsonMessage_BookDto);
        MvcResult mvcResult_BookDto = mockMvc.perform(post("/api/v1/book/")
                .content(customerJsonMessage_BookDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andReturn();

        loanTakerDto = new LibraryUserDto();
        loanTakerDto.setEmail("aurell.mikael@gmail.com");
        loanTakerDto.setName("Mikael Aurell");

        //Save LoanTakerDto(LibraryUser)
        String customerJsonMessage_LoanTakerDto = objectMapper.writeValueAsString(loanTakerDto);
        System.out.println("customerJsonMessage = " + customerJsonMessage_LoanTakerDto);
        MvcResult mvcResult_LoanTakerDto = mockMvc.perform(post("/api/v1/libraryUser/")
                .content(customerJsonMessage_LoanTakerDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andReturn();

        //Prepare TestObject
        testObject = new LoanDto();

        BookDto bookDto_Id1 = new BookDto();
        bookDto_Id1.setBookId(1);
        testObject.setBookDto(bookDto_Id1);

        LibraryUserDto loanTakerDto_Id1 = new LibraryUserDto();
        loanTakerDto_Id1.setUserId(1);
        testObject.setLoanTakerDto(loanTakerDto_Id1);

        //testObject.setLoanDate(LocalDate.of(2021,4,22));
        testObject.setTerminated(false);
    }

    @DisplayName("Save LoanDto")
    @Test
    public void save_loan_dto() throws Exception {
        String customerJsonMessage_LoanDto = objectMapper.writeValueAsString(testObject);
        System.out.println("customerJsonMessage_LoanDto = " + customerJsonMessage_LoanDto);
        MvcResult mvcResult_LoanDto = mockMvc.perform(post("/api/v1/loan/")
        .content(customerJsonMessage_LoanDto)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andReturn();
        int status = mvcResult_LoanDto.getResponse().getStatus();
        Assertions.assertEquals(201,status);
    }
}
