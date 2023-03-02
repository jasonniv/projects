package learn.resume.builder.domain;

import learn.resume.builder.data.SkillRepo;
import learn.resume.builder.models.Skill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SkillServiceTest {
    @MockBean
    SkillRepo repository;

    @Autowired
    SkillService service;
    @Test
    void shouldFindAllSkills() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill(1, "Skill One"));
        skills.add(new Skill(1, "Skill Two"));

        when(repository.findAll()).thenReturn(skills);
        List<Skill> result = service.findAll();
        assertEquals(2, skills.size());
    }
    @Test
    void shouldAddSkill(){
        Skill skill = new Skill();
        skill.setSkillName("Name");

        Result<Skill> result = service.add(skill);
        assertTrue(result.isSuccess());
    }
    @Test
    void shouldNotAddSkillIfIdIsNotZero(){
        Skill skill = new Skill();
        skill.setSkillId(1);
        skill.setSkillName("Name");

        Result<Skill> result = service.add(skill);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddNullSkill(){
        Result<Skill> result = service.add(null);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddSkillIfNameIsNull(){
        Skill skill = new Skill();
        skill.setSkillName(null);

        Result<Skill> result = service.add(skill);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }

    @Test
    void shouldNotAddSkillIfNameIsBlank(){
        Skill skill = new Skill();
        skill.setSkillName("");

        Result<Skill> result = service.add(skill);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }

    @Test
    void shouldNotDeleteNonExistentId() {
        when(repository.deleteById(11)).thenReturn(false);

        Result<Skill> result = service.deleteById(1);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldDeleteId(){
        when(repository.deleteById(1)).thenReturn(true);

        Result<Skill> result = service.deleteById(1);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldUpdate(){
        Skill skill = new Skill(1, "soup eating");

        when(repository.update(skill)).thenReturn(true);
        Result<Skill> actual = service.update(skill);

        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateForNonExistingSkillId(){
        Skill skill = new Skill(100, "soup eating");

        when(repository.update(skill)).thenReturn(false);
        Result<Skill> actual = service.update(skill);

        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldNotUpdateForNullName(){
        Skill skill = new Skill(100, null);

        when(repository.update(skill)).thenReturn(false);
        Result<Skill> actual = service.update(skill);

        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateForBlankName(){
        Skill skill = new Skill(100, "");

        when(repository.update(skill)).thenReturn(false);
        Result<Skill> actual = service.update(skill);

        assertEquals(ResultType.INVALID, actual.getType());
    }


}