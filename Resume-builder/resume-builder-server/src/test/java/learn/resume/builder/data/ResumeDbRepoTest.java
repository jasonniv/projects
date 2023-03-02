package learn.resume.builder.data;

import learn.resume.builder.models.AppRole;
import learn.resume.builder.models.AppUser;
import learn.resume.builder.models.AppUserInfo;
import learn.resume.builder.models.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResumeDbRepoTest {

    @Autowired
    ResumeDbRepo resumeRepo;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldNotGetTemplateIdByNonExistingResumeId() {
        Resume actual = resumeRepo.getByResumeId(100);

        assertNull(actual);
    }

    @Test
    void shouldGetTemplateIdByResumeId() {
        Resume actual = makeResume();
        actual = resumeRepo.getByResumeId(2);

        assertNotNull(actual);
        assertEquals(1, actual.getTemplateId());
    }

    @Test
    void shouldGetUserEmailByResumeId(){
        Resume actual = makeResume();
        actual = resumeRepo.getByResumeId(2);

        assertNotNull(actual);
        assertEquals("test email", actual.getUserInfo().getEmail());
    }

    @Test
    void shouldGetListOfResumeByUserId(){
        List<Resume> actual = resumeRepo.getResumesByUser(1);

        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    void shouldNotGetListOfResumeByNonExistingUserId(){
        List<Resume> actual = resumeRepo.getResumesByUser(100);
        assertNull(actual);
    }

    @Test
    void shouldAddResume(){

        AppUserInfo userInfo = new AppUserInfo();
        userInfo.setInfoId(2);
        userInfo.setEmail("test email");
        userInfo.setFirstName("testFirst");
        userInfo.setLastName("testLast");
        userInfo.setAddress("testaddress");
        userInfo.setPhoneNumber("123456");

        List<AppRole> roles = new ArrayList<>();
        roles.add(new AppRole(1, "Job Seeker"));
        roles.add(new AppRole(2, "Employer"));

        AppUser user = new AppUser(1,"jasonniv", "$2y$10$Gk9DNFuQNRhSYSDZ.xk3CO65dJ6wz3snAd2rdrVUTWcfUzrxHr5hq", false, roles);

        Resume resumeToTest = new Resume();
        resumeToTest.setTemplateId(2);
        resumeToTest.setUserInfo(userInfo);
        resumeToTest.setUser(user);

        Resume actual = resumeRepo.add(resumeToTest);

        assertNotNull(actual);
        assertEquals(3, actual.getResumeId());
    }

    @Test
    void shouldNotAddResumeWithNoUserInfo(){

        List<AppRole> roles = new ArrayList<>();
        roles.add(new AppRole(1, "Job Seeker"));
        roles.add(new AppRole(2, "Employer"));

        AppUser user = new AppUser(1,"jasonniv", "$2y$10$Gk9DNFuQNRhSYSDZ.xk3CO65dJ6wz3snAd2rdrVUTWcfUzrxHr5hq", false, roles);

        Resume resumeToTest = new Resume();
        resumeToTest.setTemplateId(2);
        resumeToTest.setUserInfo(null);
        resumeToTest.setUser(user);

        Resume actual = resumeRepo.add(resumeToTest);

        assertNull(actual);
    }

    @Test
    void shouldNotAddResumeWithNoUser(){

        AppUserInfo userInfo = new AppUserInfo();
        userInfo.setInfoId(2);
        userInfo.setEmail("test email");
        userInfo.setFirstName("testFirst");
        userInfo.setLastName("testLast");
        userInfo.setAddress("testaddress");
        userInfo.setPhoneNumber("123456");

        List<AppRole> roles = new ArrayList<>();
        roles.add(new AppRole(1, "Job Seeker"));
        roles.add(new AppRole(2, "Employer"));

        AppUser user = new AppUser(1,"jasonniv", "$2y$10$Gk9DNFuQNRhSYSDZ.xk3CO65dJ6wz3snAd2rdrVUTWcfUzrxHr5hq", false, roles);

        Resume resumeToTest = new Resume();
        resumeToTest.setTemplateId(2);
        resumeToTest.setUserInfo(userInfo);
        resumeToTest.setUser(null);

        Resume actual = resumeRepo.add(resumeToTest);

        assertNull(actual);
    }

    @Test
    void shouldAddResumeWithNullEducation(){

        Resume resumeToTest = makeResume();
        resumeToTest.setEducations(null);

        Resume actual = resumeRepo.add(resumeToTest);

        assertNotNull(actual);
        assertEquals(3, actual.getResumeId());
    }
    @Test
    void shouldAddResumeWithNullSkill(){

        Resume resumeToTest = makeResume();
        resumeToTest.setSkills(null);

        Resume actual = resumeRepo.add(resumeToTest);

        assertNotNull(actual);
        assertEquals(3, actual.getResumeId());
    }

    @Test
    void shouldAddResumeWithNullWorkHistories(){

        Resume resumeToTest = makeResume();
        resumeToTest.setWorkHistories(null);

        Resume actual = resumeRepo.add(resumeToTest);

        assertNotNull(actual);
        assertEquals(3, actual.getResumeId());
    }

    @Test
    void shouldDeleteByResumeId(){
        assertTrue(resumeRepo.deleteByResumeId(1));
        assertFalse(resumeRepo.deleteByResumeId(1));

        assertTrue(resumeRepo.deleteByResumeId(2));
        assertFalse(resumeRepo.deleteByResumeId(2));
    }

    @Test
    void shouldNotDeleteNonExistingResumeId(){
        assertFalse(resumeRepo.deleteByResumeId(100));
    }

    Resume makeResume(){
        AppUserInfo userInfo = new AppUserInfo();
        userInfo.setInfoId(2);
        userInfo.setEmail("test email");
        userInfo.setFirstName("testFirst");
        userInfo.setLastName("testLast");
        userInfo.setAddress("testaddress");
        userInfo.setPhoneNumber("123456");

        List<AppRole> roles = new ArrayList<>();
        roles.add(new AppRole(1, "Job Seeker"));
        roles.add(new AppRole(2, "Employer"));

        AppUser user = new AppUser(1,"jasonniv", "$2y$10$Gk9DNFuQNRhSYSDZ.xk3CO65dJ6wz3snAd2rdrVUTWcfUzrxHr5hq", false, roles);

        Resume resumeToTest = new Resume();
        resumeToTest.setTemplateId(2);
        resumeToTest.setUserInfo(userInfo);
        resumeToTest.setUser(user);

        return resumeToTest;
    }

}