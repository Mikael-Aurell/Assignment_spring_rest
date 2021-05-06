package se.lexicon.booklender.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    String url = "/api/v1/loan/";

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
        objectMapper.registerModule(new JavaTimeModule());

        bookDto = new BookDto();
        bookDto.setTitle("How to Become a senor Java Fullstack Developer");
        bookDto.setAvailable(true);
        bookDto.setReserved(false);
        bookDto.setMaxLoanDays(30);
        bookDto.setFinePerDay(BigDecimal.valueOf(1 / 8));
        bookDto.setDescription("Java");

        //Save BookDto
        String customerJsonMessage_BookDto = objectMapper.writeValueAsString(bookDto); //Object to Json (Serialize)
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

        //Set bookId to 1 to fetch from db when creating LoanDtoObject
        BookDto bookDto_Id1 = new BookDto();
        bookDto_Id1.setBookId(1);
        testObject.setBookDto(bookDto_Id1);

        //Set userId to 1 to fetch from db when creating LoanDtoObject
        LibraryUserDto loanTakerDto_Id1 = new LibraryUserDto();
        loanTakerDto_Id1.setUserId(1);
        testObject.setLoanTakerDto(loanTakerDto_Id1);

        testObject.setLoanDate(LocalDate.of(2021,4,22));
        testObject.setTerminated(false);

        String customerJsonMessage_LoanDto = objectMapper.writeValueAsString(testObject);
        System.out.println("customerJsonMessage_LoanDto = " + customerJsonMessage_LoanDto);
        MvcResult mvcResult_LoanDto = mockMvc.perform(post(url)
                .content("{\"loanTakerDto\": {\"userId\": 1},\n" +
                        "\"bookDto\": {\"bookId\": 1},\n" +
                        "\"loanDate\": null,\n" +
                        "\"terminated\": true }")
                //.content(customerJsonMessage_LoanDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn();
        int status = mvcResult_LoanDto.getResponse().getStatus();
        Assertions.assertEquals(201,status);
    }

    @DisplayName("Save Loan")
    @Test
    public void save_loan_dto() throws Exception {
        mockMvc.perform(get(url)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Update Loan")
    @Test
    public void update_loan() throws Exception {

        testObject.setLoanId(1);

        String customerJsonMessage_LoanDto_update = objectMapper.writeValueAsString(testObject);
        mockMvc.perform(put(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .content(customerJsonMessage_LoanDto_update))
                .andExpect(status().isOk())
                .andReturn();
                /*.content("{\n" +
                        "    \"loanId\": 1,\n" + "loanTakerDto\": {\"userId\": 1},\n" +
                        "\"bookDto\": {\"bookId\": 1},\n" +
                        "\"loanDate\": null,\n" +
                        "\"terminated\": false }"))*/


        mockMvc.perform(get(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].terminated",is(true)))
                .andDo(print())
                .andReturn();
    }


    @DisplayName("Find Loan by Id 1")
    @Test
    public void find_loan_by_id_1() throws Exception {

        mockMvc.perform(get(url+"1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk())
                .andReturn();
    }

    @DisplayName("Find Loan by Book Id 1")
    @Test
    public void find_loan_by_book_id_1() throws Exception {

        mockMvc.perform(get(url+"book/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk())
                .andReturn();
    }

    @DisplayName("Find Loan by LoanTaker Id 1")
    @Test
    public void find_loan_by_loanTaker_id_1() throws Exception {

        mockMvc.perform(get(url+"loanTaker/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk())
                .andReturn();
    }

    @DisplayName("Find All Books")
    @Test
    public void find_all_loans() throws Exception {

        mockMvc.
                perform(
                        get(url)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print())
                .andExpect(jsonPath("$[0].bookDto.title", is("How to Become a senor Java Fullstack Developer")))
                .andReturn();
    }

    @DisplayName("Delete Book with Id 1")
    @Test
    public  void delete_loan_with_Id_1() throws Exception {

        mockMvc.
                perform(
                        delete(url+"1")
                )
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.
                perform(
                        get(url)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(0)))
                .andReturn();
    }
}
