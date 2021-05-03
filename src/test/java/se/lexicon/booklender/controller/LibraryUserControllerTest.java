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
import se.lexicon.booklender.dto.LibraryUserDto;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private LibraryUserDto libraryUserDto;
    private ObjectMapper objectMapper;

    public LibraryUserControllerTest() throws Exception {
    }

    @BeforeEach
    public void setup(){
        objectMapper = new ObjectMapper();

        libraryUserDto = new LibraryUserDto();
        //libraryUserDto.setUserId(1);
        libraryUserDto.setEmail("aurell.mikael@gmail.com");
        libraryUserDto.setName("Mikael Aurell");
        //libraryUserDto.setRegDate(LocalDate.of(2021,5,3));
    }

    @DisplayName("Save LibraryUserDto")
    @Test
    public void save_library_user_dto() throws Exception {
        String customerJsonMessage = objectMapper.writeValueAsString(libraryUserDto);
        System.out.println("customerJsonMessage = " + customerJsonMessage);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/libraryUser/")
                .content(customerJsonMessage)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(201,status);
    }

}
