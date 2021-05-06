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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private LibraryUserDto libraryUserDto;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        objectMapper = new ObjectMapper();

        libraryUserDto = new LibraryUserDto();
        libraryUserDto.setEmail("aurell.mikael@gmail.com");
        libraryUserDto.setName("Mikael Aurell");
        //libraryUserDto.setRegDate(LocalDate.of(2021,5,3));
    }

    @DisplayName("Save LibraryUser")
    @Test
    public void save_library_user() throws Exception {
        String customerJsonMessage = objectMapper.writeValueAsString(libraryUserDto);
        System.out.println("customerJsonMessage = " + customerJsonMessage);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/libraryUser/")
                .content(customerJsonMessage)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(201,status);
    }

    @DisplayName("Update LibraryUser")
    @Test
    public void update_library_user() throws Exception {
        save_library_user();

        mockMvc.perform(put("/api/v1/libraryUser/")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        .content("{\n" +
                "    \"userId\": 1,\n" +
                "    \"name\": \"Mikael Aurell update\",\n" +
                "    \"email\": \"aurell.mikael@gmail.com\"\n" +
                "    }"))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get("/api/v1/libraryUser/")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name",is("Mikael Aurell update")))
                .andDo(print())
                .andReturn();
    }


    @DisplayName("Find LibraryUser by Id 1")
    @Test
    public void find_library_user_by_id_1() throws Exception {
        save_library_user();
        mockMvc.perform(get("/api/v1/libraryUser/1")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk())
                .andReturn();
    }

    @DisplayName("Find All LibraryUser")
    @Test
    public void find_all_LibraryUser() throws Exception {
        save_library_user();

        mockMvc.
                perform(
                        get("/api/v1/libraryUser/")
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Mikael Aurell")))
                .andReturn();
    }

    @DisplayName("Delete LibraryUser with Id 1")
    @Test
    public  void delete_libraryUser_with_Id_1() throws Exception {
        save_library_user();

        mockMvc.
                perform(
                        delete("/api/v1/libraryUser/1")
                )
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.
                perform(
                        get("/api/v1/libraryUser/")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(0)))
                .andReturn();
    }

}
