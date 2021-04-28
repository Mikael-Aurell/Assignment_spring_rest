package se.lexicon.booklender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.booklender.dto.LibraryUserDto;
import se.lexicon.booklender.entity.LibraryUser;
import se.lexicon.booklender.exception.DataNotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LibraryUserServiceImplTest {
    LibraryUserService testObject;
    LibraryUserDto testLibraryUserDto;
    LibraryUserDto testLibraryUser2Dto;

    @Autowired
    public void setTestObject(LibraryUserService testObject) {
        this.testObject = testObject;
    }

    @BeforeEach
    public void setup(){
        testLibraryUserDto = new LibraryUserDto();
        testLibraryUserDto.setRegDate(LocalDate.of(1978, 4, 30));
        testLibraryUserDto.setName("Mikael Aurell");
        testLibraryUserDto.setEmail("aurell.mikael@gmail.com");

        testLibraryUser2Dto = new LibraryUserDto();
        testLibraryUser2Dto.setRegDate(LocalDate.of(1980, 6, 13));
        testLibraryUser2Dto.setName("Mikael2 Aurell");
        testLibraryUser2Dto.setEmail("aurell.mikael2@gmail.com");

        testObject.create(testLibraryUserDto);
    }

    @Test
    @DisplayName("Test_1: Find By Id ")
    public void test_find_by_id(){
        testObject.create(testLibraryUser2Dto);
        try {
            assertEquals("Mikael2 Aurell",testObject.findById(2).getName());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test_2: Create LibraryUser")
    public void test_create_book(){
        assertEquals("Mikael2 Aurell",testObject.create(testLibraryUser2Dto).getName());
    }

    @Test
    @DisplayName("Test_3: Update LibraryUser")
    public void test_update_book() {
        testLibraryUser2Dto.setUserId(1);
        try {
            assertEquals("Mikael2 Aurell", testObject.update(testLibraryUser2Dto).getName());
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test_4: Delete")
    public void test_delete(){
        testObject.create(testLibraryUser2Dto);
        assertEquals(2,testObject.findAll().size());
        try {
            testObject.delete(2);  //void instead of returning boolean
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(1, testObject.findAll().size());
    }

    @Test
    @DisplayName("Test_5: FindAll")
    public void test_findAll(){
        testObject.create(testLibraryUser2Dto);
        assertEquals("Mikael Aurell", testObject.findAll().get(0).getName());
        assertEquals("aurell.mikael2@gmail.com", testObject.findAll().get(1).getEmail());
    }

    @Test
    @DisplayName("Test_6: Find By Email")
    public void test_find_by_email(){
        assertEquals(testLibraryUserDto.getRegDate(),testObject.findByEmail("aurell.mikael@gmail.com").getRegDate());
    }
}
