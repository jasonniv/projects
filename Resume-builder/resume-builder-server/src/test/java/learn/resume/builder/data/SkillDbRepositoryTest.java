package learn.resume.builder.data;

import learn.resume.builder.models.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SkillDbRepositoryTest {

    @Autowired
    SkillDbRepository repository;

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
        List<Skill> actual = repository.findAll();

        assertNotNull(actual);
        assertTrue(actual.size() >= 3);
        Skill s1 = actual.stream()
                .filter(skill -> skill.getSkillId() == 1)
                .findFirst().orElse(null);
        Skill s2 = actual.stream()
                .filter(skill -> skill.getSkillId() == 2)
                .findFirst().orElse(null);
        Skill s3 = actual.stream()
                .filter(skill -> skill.getSkillId() == 3)
                .findFirst().orElse(null);

        assertEquals(1, s1.getSkillId());
        assertEquals("sing", s1.getSkillName());

        assertEquals(2, s2.getSkillId());
        assertEquals("dance", s2.getSkillName());

        assertEquals(3, s3.getSkillId());
        assertEquals("cook", s3.getSkillName());

    }
    @Test
    void shouldFindSkillByResumeId(){
        List<Skill> skill = new ArrayList<>();
        skill.add(new Skill(1, "sing"));

        List<Skill> actual = repository.getSkillByResumeId(1);
        assertEquals(actual.size(), skill.size());
    }
    @Test
    void shouldNotFindSkillByResumeId(){
        List<Skill> skill = new ArrayList<>();
        skill.add(new Skill(1, "sing"));
        skill.add(new Skill(1, "dance"));

        List<Skill> actual = repository.getSkillByResumeId(1);
        assertNotEquals(actual.size(), skill.size());
    }
    @Test
    void shouldAddSkill(){
        Skill skill = new Skill();
        skill.setSkillName("act");

        Skill actual = repository.add(skill);
        assertEquals(actual.getSkillName(), skill.getSkillName());
    }
    @Test
    void shouldDeleteExitingId(){
        assertTrue(repository.deleteById(1));
    }

    @Test
    void shouldNotDeleteByNonExistingId(){
        assertFalse(repository.deleteById(10));
    }

    @Test
    void shouldUpdate(){
        Skill skill = new Skill(2, "jumping");
        assertTrue(repository.update(skill));
    }

    @Test
    void shouldNotUpdateNonExistingId(){
        Skill skill = new Skill(1000, "jumping");
        assertFalse(repository.update(skill));
    }

}