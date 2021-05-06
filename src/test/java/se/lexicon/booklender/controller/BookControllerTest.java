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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.lexicon.booklender.dto.BookDto;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private BookDto bookDto;
    private ObjectMapper objectMapper;

    private String url = "/api/v1/book/";

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

        String customerJsonMessage = objectMapper.writeValueAsString(bookDto);
        System.out.println("customerJsonMessage = " + customerJsonMessage);
        MvcResult mvcResult = mockMvc.perform(post(url)
                .content(customerJsonMessage)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(201,status);
    }

    //Normal Tests

    @DisplayName("Save BookDto")
    @Test
    public void save_book() throws Exception {
        mockMvc.perform(get("url")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @DisplayName("Update LibraryUser")
    @Test
    public void update_library_user() throws Exception {

        mockMvc.perform(put(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"bookId\": 1,\n" +
                        "    \"title\": \"How to Become a senor Java Fullstack Developer Update\",\n" +
                        "    \"available\": true,\n" +
                        "    \"reserved\": false,\n" +
                        "    \"maxLoanDays\": 30,\n" +
                        "    \"finePerDay\": 0.125,\n" +
                        "    \"description\": \"Java\"}"))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title",is("How to Become a senor Java Fullstack Developer Update")))
                .andDo(print())
                .andReturn();
    }


    @DisplayName("Find Book by Id 1")
    @Test
    public void find_book_by_id_1() throws Exception {

        mockMvc.perform(get(url+"1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk())
                .andReturn();
    }

    @DisplayName("Find All Books")
    @Test
    public void find_all_Books() throws Exception {

        mockMvc.
                perform(
                        get(url)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("How to Become a senor Java Fullstack Developer")))
                .andReturn();
    }

    @DisplayName("Delete Book with Id 1")
    @Test
    public  void delete_book_with_Id_1() throws Exception {

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

    //Validation Tests

    @DisplayName("Save invalid fields to BookDto")
    @Test
    public void save_invalid_fields() throws Exception {
        delete_book_with_Id_1();

        objectMapper = new ObjectMapper();

        BookDto bookDto2 = new BookDto();
        bookDto2.setTitle("A");
        bookDto2.setAvailable(true);
        bookDto2.setReserved(false);
        bookDto2.setMaxLoanDays(30);
        bookDto2.setFinePerDay(BigDecimal.valueOf(1 / 8));
        bookDto2.setDescription("Java");

        String customerJsonMessage = objectMapper.writeValueAsString(bookDto2);
        System.out.println("customerJsonMessage = " + customerJsonMessage);
        MvcResult mvcResult = mockMvc.perform(post(url)
                .content(customerJsonMessage)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andReturn();
        int actual = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(400, actual);

    }
}
