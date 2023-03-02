package learn.resume.builder.domain;


import learn.resume.builder.data.ResumeRepo;
import learn.resume.builder.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ResumeServiceTest {

    @MockBean
    ResumeRepo resumeRepo;

    @Autowired
    ResumeService service;

    @Test
    void shouldFindExpectedResumeByResumeId() {
        Resume expected = makeResume();

        when(resumeRepo.getByResumeId(1)).thenReturn(expected);

        Resume actual = service.findByResumeId(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindResumeCuzOfNonExistingResume(){
        Resume expected = makeResume();

        when(resumeRepo.getByResumeId(1)).thenReturn(expected);
        Resume actual = service.findByResumeId(100);
        assertNull(actual);
    }

    @Test
    void shouldFindListOfResumeByUserId(){

        List<AppRole> roles = new ArrayList<>();
        roles.add(new AppRole(1, "Job Seeker"));
        roles.add(new AppRole(2, "Employer"));

        AppUser user = new AppUser(1,"jasonniv", "$2y$10$Gk9DNFuQNRhSYSDZ.xk3CO65dJ6wz3snAd2rdrVUTWcfUzrxHr5hq", false, roles);

        AppUserInfo userInfo = new AppUserInfo();
        userInfo.setInfoId(1);
        userInfo.setEmail("jason@gmail.com");
        userInfo.setFirstName("jason");
        userInfo.setLastName("oh");
        userInfo.setAddress("testaddress");
        userInfo.setPhoneNumber("123456789");

        Resume toAddResume = new Resume();
        toAddResume.setResumeId(1);
        toAddResume.setTemplateId(1);
        toAddResume.setUserInfo(userInfo);
        toAddResume.setUser(user);

        List<Resume> expectedResume = new ArrayList<>();
        expectedResume.add(toAddResume);

        when(resumeRepo.getResumesByUser(1)).thenReturn(expectedResume);

        Result<List<Resume>> actualResume = service.getResumesByUser(1);

        assertArrayEquals(expectedResume.toArray(), actualResume.getPayload().toArray());


    }

    @Test
    void shouldAddResume(){
        Resume resume = makeResume();
        resume.setTemplateId(2);

        Resume mockOut = makeResume();
        resume.setTemplateId(2);

        when(resumeRepo.add(resume)).thenReturn(mockOut);

        Result<Resume> actual = service.addResume(resume);
        assertEquals(mockOut, actual.getPayload());
    }

    @Test
    void shouldNotAddWhenUserInfoIsNull(){
        Resume resume = makeResume();
        resume.setTemplateId(2);
        resume.setUserInfo(null);

        Resume mockOut = makeResume();
        resume.setTemplateId(1);

        when(resumeRepo.add(resume)).thenReturn(mockOut);

        Result<Resume> actual = service.addResume(resume);
        assertEquals(ResultType.INVALID, actual.getType());

    }

    @Test
    void shouldNotAddWhenUserIsNull(){
        Resume resume = makeResume();
        resume.setTemplateId(2);
        resume.setUser(null);

        Resume mockOut = makeResume();
        resume.setTemplateId(1);

        when(resumeRepo.add(resume)).thenReturn(mockOut);

        Result<Resume> actual = service.addResume(resume);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldAddWhenEducationIsNull(){
        Resume resume = makeResume();
        resume.setTemplateId(2);
        resume.setEducations(null);

        Resume mockOut = makeResume();
        resume.setTemplateId(1);

        when(resumeRepo.add(resume)).thenReturn(mockOut);

        Result<Resume> actual = service.addResume(resume);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldAddWhenSkillIsNull(){
        Resume resume = makeResume();
        resume.setTemplateId(2);
        resume.setSkills(null);

        Resume mockOut = makeResume();
        resume.setTemplateId(1);

        when(resumeRepo.add(resume)).thenReturn(mockOut);

        Result<Resume> actual = service.addResume(resume);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldAddWhenWorkHistoryIsNull(){
        Resume resume = makeResume();
        resume.setTemplateId(2);
        resume.setWorkHistories(null);

        Resume mockOut = makeResume();
        resume.setTemplateId(1);

        when(resumeRepo.add(resume)).thenReturn(mockOut);

        Result<Resume> actual = service.addResume(resume);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldDeleteResumeByResumeId(){
        Resume resume = makeResume();
        resume.setResumeId(1);
        when(resumeRepo.getByResumeId(1)).thenReturn(resume).thenReturn(null);
        when(resumeRepo.deleteByResumeId(1)).thenReturn(true);

        Resume found = service.findByResumeId(1);
        Result<Resume> deletedResume = service.deleteByResumeId(1);
        Resume nulLResume = service.findByResumeId(1);

        assertNotNull(found);
        assertTrue(deletedResume.isSuccess());
        assertNull(nulLResume);
    }

    @Test
    void shouldNotDeleteResumeByNonExistingResumeId(){

        when(resumeRepo.deleteByResumeId(100)).thenReturn(false);

        Result<Resume> deletedResult = service.deleteByResumeId(100);

        assertFalse(deletedResult.isSuccess());
    }

    @Test
    void shouldUpdateTemplateId(){
        Resume resume = makeResume();
        resume.setResumeId(1);
        resume.setTemplateId(20);

        when(resumeRepo.update(resume)).thenReturn(true);

        Result<Resume> actual = service.updateResume(resume);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotUpdateWithBlankTemplateId(){
        Resume resume = makeResume();
        resume.setResumeId(1);
        resume.setTemplateId(0);

        when(resumeRepo.update(resume)).thenReturn(false);

        Result<Resume> actual = service.updateResume(resume);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateWithNullUser(){
        Resume resume = makeResume();
        resume.setResumeId(1);
        resume.setUser(null);

        when(resumeRepo.update(resume)).thenReturn(false);

        Result<Resume> actual = service.updateResume(resume);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateWithNullUserInfo(){
        Resume resume = makeResume();
        resume.setResumeId(1);
        resume.setUserInfo(null);

        when(resumeRepo.update(resume)).thenReturn(false);

        Result<Resume> actual = service.updateResume(resume);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotUpdateWithNonExistingId(){
        Resume resume = makeResume();
        resume.setResumeId(200);

        when(resumeRepo.update(resume)).thenReturn(false);

        Result<Resume> actual = service.updateResume(resume);
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }
    Resume makeResume(){

        AppUserInfo userInfo = new AppUserInfo();
        userInfo.setInfoId(1);
        userInfo.setEmail("jason@gmail.com");
        userInfo.setFirstName("jason");
        userInfo.setLastName("oh");
        userInfo.setAddress("testaddress");
        userInfo.setPhoneNumber("123456789");

        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill(10, "sing"));

        List<Education> educations = new ArrayList<>();
        educations.add(new Education(10,"College","Bachelors"));

        List<WorkHistory> workHistories = new ArrayList<>();
        workHistories.add(new WorkHistory(10,"Dev10", "Fighter", LocalDate.of(2010,6,16), LocalDate.of(2010,10,16), "I sing and won American Idol"));

        List<AppRole> roles = new ArrayList<>();
        roles.add(new AppRole(1, "Job Seeker"));
        roles.add(new AppRole(2, "Employer"));

        AppUser user = new AppUser(1,"jasonniv", "$2y$10$Gk9DNFuQNRhSYSDZ.xk3CO65dJ6wz3snAd2rdrVUTWcfUzrxHr5hq", false, roles);

        Resume resume = new Resume();
        resume.setTemplateId(1);
        resume.setUser(user);
        resume.setUserInfo(userInfo);
        resume.setSkills(skills);
        resume.setEducations(educations);
        resume.setWorkHistories(workHistories);

        return resume;
    }
}