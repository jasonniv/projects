package learn.resume.builder.domain;

import learn.resume.builder.data.EducationRepo;
import learn.resume.builder.models.Education;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class EducationServiceTest {
    @MockBean
    EducationRepo repository;

    @Autowired
    EducationService service;

    @Test
    void shouldFindAllEducation() {
        List<Education> educations = new ArrayList<>();
        educations.add(new Education(1, "Education", "Education_level"));
        educations.add(new Education(1, "EducationTwo", "Education_level_two"));

        when(repository.findAll()).thenReturn(educations);
        List<Education> result = service.findAll();
        assertEquals(2, educations.size());
    }

    @Test
    void shouldAddEducation(){
        Education education = new Education();
        education.setSchoolName("name");
        education.setEducationLevel("level");

        Result<Education> result = service.add(education);
        assertTrue(result.isSuccess());
    }
    @Test
    void shouldNotAddEducationIfIdIsNotZero(){
        Education education = new Education();
        education.setEducationId(1);
        education.setSchoolName("name");
        education.setEducationLevel("level");

        Result<Education> result = service.add(education);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddIfEducationIsNull(){
        Result<Education> result = service.add(null);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddIfEducationNameIsNull(){
        Education education = new Education();
        education.setSchoolName(null);
        education.setEducationLevel("level");

        Result<Education> result = service.add(education);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddIfEducationNameIsBlank(){
        Education education = new Education();
        education.setSchoolName("");
        education.setEducationLevel("level");

        Result<Education> result = service.add(education);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddIfEducationLevelIsNull(){
        Education education = new Education();
        education.setSchoolName("name");
        education.setEducationLevel(null);

        Result<Education> result = service.add(education);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddIfEducationLevelIsBlank(){
        Education education = new Education();
        education.setSchoolName("name");
        education.setEducationLevel("");

        Result<Education> result = service.add(education);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotDeleteNonExistentId() {
        when(repository.deleteById(11)).thenReturn(false);

        Result<Education> result = service.deleteById(1);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldDeleteId(){
        when(repository.deleteById(1)).thenReturn(true);

        Result<Education> result = service.deleteById(1);
        assertTrue(result.isSuccess());
    }
    @Test
    void shouldUpdate() {
        Education education = new Education(1, "school_name", "level");
        when(repository.update(education)).thenReturn(true);

        Result<Education> result = service.update(education);
        assertTrue(result.isSuccess());
    }
    @Test
    void shouldNotUpdateNullSchoolName() {
        Education education = new Education(1, null, "level");
        when(repository.update(education)).thenReturn(false);

        Result<Education> result = service.update(education);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldNotUpdateBlankSchoolName() {
        Education education = new Education(1, "", "level");
        when(repository.update(education)).thenReturn(false);

        Result<Education> result = service.update(education);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldNotUpdateBlankEducationLevel() {
        Education education = new Education(1, "school_name", "");
        when(repository.update(education)).thenReturn(false);

        Result<Education> result = service.update(education);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldNotUpdateNullEducationLevel() {
        Education education = new Education(1, "school_name", null);
        when(repository.update(education)).thenReturn(false);

        Result<Education> result = service.update(education);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldNotUpdate() {
        Education education = new Education(111, "school_name", "level");
        when(repository.update(education)).thenReturn(false);

        Result<Education> result = service.update(education);
        assertFalse(result.isSuccess());
    }
}