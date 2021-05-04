package se.lexicon.booklender.controller;

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

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private BookDto bookDto;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        bookDto = new BookDto();
        bookDto.setTitle("How to Become a senor Java Fullstack Developer");
        bookDto.setAvailable(true);
        bookDto.setReserved(false);
        bookDto.setMaxLoanDays(30);
        bookDto.setFinePerDay(BigDecimal.valueOf(1 / 8));
        bookDto.setDescription("Java");
    }

    @DisplayName("Save BookDto")
    @Test
    public void save_book_dto() throws Exception {
        String customerJsonMessage = objectMapper.writeValueAsString(bookDto);
        System.out.println("customerJsonMessage = " + customerJsonMessage);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/book/")
                .content(customerJsonMessage)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(201,status);
    }
}
