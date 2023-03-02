package learn.resume.builder.data;

import learn.resume.builder.models.WorkHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WorkHistoryDbRepositoryTest {

    @Autowired
    WorkHistoryDbRepository repository;

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
        List<WorkHistory> actual = repository.findAll();

        assertNotNull(actual);

        WorkHistory w1 = actual.stream()
                .filter(workHistory -> workHistory.getWorkHistoryId() == 1 )
                .findFirst().orElse(null);
        WorkHistory w2 = actual.stream()
                .filter(workHistory -> workHistory.getWorkHistoryId() == 2 )
                .findFirst().orElse(null);

        assertEquals(1, w1.getWorkHistoryId());
        assertEquals("Singer", w1.getJobTitle());
        assertEquals("2010-06-16", w1.getStartDate().toString());
        assertEquals("2010-10-16", w1.getEndDate().toString());
        assertEquals("I sing and won American Idol", w1.getJobDescription());

        assertEquals(2, w2.getWorkHistoryId());
        assertEquals("Dancer", w2.getJobTitle());
        assertEquals("2010-11-16", w2.getStartDate().toString());
        assertEquals("2010-12-16", w2.getEndDate().toString());
        assertEquals("I dance and I am a ballerina", w2.getJobDescription());
    }

    @Test
    void shouldFindWorkHistoryByResumeId(){
        List<WorkHistory> workHistory = new ArrayList<>();
        workHistory.add(new WorkHistory(1,"Dev10", "Singer",
                LocalDate.of(2010, 06,16),LocalDate.of(2010,10,16),
                "I sing and won American Idol"));

        List<WorkHistory> actual = repository.getWorkHistoryByResumeId(1);
        assertEquals(actual.size(), workHistory.size());
    }
    @Test
    void shouldNotFindWrongWorkHistoryByResumeId(){
        List<WorkHistory> workHistory = new ArrayList<>();
        workHistory.add(new WorkHistory(1,"Dev10", "Singer",
                LocalDate.of(2010, 06,16),LocalDate.of(2010,10,16),
                "I sing and won American Idol"));
        workHistory.add(new WorkHistory(2,"Dev10", "Dancer",
                LocalDate.of(2011, 06,16),LocalDate.of(2013,10,16),
                "I dance and won Dancing w/ the Stars"));

        List<WorkHistory> actual = repository.getWorkHistoryByResumeId(1);
        assertNotEquals(actual.size(), workHistory.size());
    }
    @Test
    void shouldAddWorkHistory() {
        WorkHistory workHistory = new WorkHistory();
        workHistory.setJobTitle("Title");
        workHistory.setStartDate(LocalDate.of(2013, 7,19));
        workHistory.setEndDate(LocalDate.of(2014, 9,19));
        workHistory.setJobDescription("description");

        WorkHistory actual = repository.add(workHistory);
        assertEquals(actual.getJobTitle(), workHistory.getJobTitle());
        assertEquals(actual.getStartDate(), workHistory.getStartDate());
        assertEquals(actual.getEndDate(), workHistory.getEndDate());
        assertEquals(actual.getJobDescription(), workHistory.getJobDescription());
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