package learn.resume.builder.data;

import learn.resume.builder.models.Education;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EducationDbRepositoryTest {
    @Autowired
    EducationDbRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findAll() {
        List<Education> actual = repository.findAll();

        assertNotNull(actual);
        assertTrue(actual.size() >= 1);
        Education e1 = actual.stream()
                .filter(education -> education.getEducationId() == 1)
                .findFirst().orElse(null);

        assertEquals(1, e1.getEducationId());
        assertEquals("College", e1.getSchoolName());
        assertEquals("Bachelors", e1.getEducationLevel());
    }
    @Test
    void shouldFindEducationByResumeId(){
        List<Education> education = new ArrayList<>();
        education.add(new Education(1, "College", "Bachelors"));

        List<Education> actual = repository.getEducationByResumeId(1);
        assertEquals(actual.size(), education.size());
    }
    @Test
    void shouldNotFindEducationByResumeId(){
        List<Education> education = new ArrayList<>();
        education.add(new Education(1, "College", "Bachelors"));
        education.add(new Education(2, "College", "Bachelors"));

        List<Education> actual = repository.getEducationByResumeId(1);
        assertNotEquals(actual.size(), education.size());
    }
    @Test
    void shouldAddEducation(){
        Education education = new Education();
        education.setSchoolName("name");
        education.setEducationLevel("level");

        Education actual = repository.add(education);
        assertEquals(actual.getSchoolName(), education.getSchoolName());
        assertEquals(actual.getEducationLevel(), education.getEducationLevel());
    }
    @Test
    void shouldDeleteExitingId(){
        assertTrue(repository.deleteById(1));
    }

    @Test
    void shouldNotDeleteByNonExistingId(){
        assertFalse(repository.deleteById(10));
    }
}