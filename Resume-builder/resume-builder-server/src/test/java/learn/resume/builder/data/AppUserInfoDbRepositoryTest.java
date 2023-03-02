package learn.resume.builder.data;

import learn.resume.builder.models.AppUserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserInfoDbRepositoryTest {

    @Autowired
    AppUserInfoDbRepository infoRepo;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void findAll() {
        List<AppUserInfo> actual = infoRepo.findAll();

        assertNotNull(actual);
        assertTrue(actual.size() >= 1);
        AppUserInfo aui = actual.stream()
                .filter(appUserInfo -> appUserInfo.getInfoId() == 1)
                .findFirst().orElse(null);

        assertEquals(1, aui.getInfoId());
        assertEquals("jason@gmail.com", aui.getEmail());
        assertEquals("jason", aui.getFirstName());
        assertEquals("oh", aui.getLastName());
        assertEquals("testaddress", aui.getAddress());
        assertEquals("123456789", aui.getPhoneNumber());
    }

    @Test
    void shouldNotFindUserInfoByNonExistingResumeId(){
        AppUserInfo actual = infoRepo.findUserByResumeId(100);
        assertNull(actual);
    }

    @Test
    void shouldFindEmailByResumeId() {
        AppUserInfo actual = infoRepo.findUserByResumeId(1);

        assertNotNull(actual);
        assertEquals("jason@gmail.com", actual.getEmail());
    }

    @Test
    void shouldFindFirstNameByResumeId() {
        AppUserInfo actual = infoRepo.findUserByResumeId(1);

        assertNotNull(actual);
        assertEquals("jason", actual.getFirstName());
    }

    @Test
    void shouldFindLastNameByResumeId() {
        AppUserInfo actual = infoRepo.findUserByResumeId(1);

        assertNotNull(actual);
        assertEquals("oh", actual.getLastName());
    }

    @Test
    void shouldFindAddressByResumeId() {
        AppUserInfo actual = infoRepo.findUserByResumeId(1);

        assertNotNull(actual);
        assertEquals("testaddress", actual.getAddress());
    }

    @Test
    void shouldFindPhoneNumberByResumeId() {
        AppUserInfo actual = infoRepo.findUserByResumeId(1);

        assertNotNull(actual);
        assertEquals("123456789", actual.getPhoneNumber());
    }

    @Test
    void shouldAddUserInfo(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setEmail("email");
        appUserInfo.setFirstName("fname");
        appUserInfo.setLastName("lname");
        appUserInfo.setAddress("address");
        appUserInfo.setPhoneNumber("pn");

        AppUserInfo actual = infoRepo.add(appUserInfo);
        assertEquals(actual.getEmail(), appUserInfo.getEmail());
        assertEquals(actual.getFirstName(), appUserInfo.getFirstName());
        assertEquals(actual.getLastName(), appUserInfo.getLastName());
        assertEquals(actual.getAddress(), appUserInfo.getAddress());
        assertEquals(actual.getPhoneNumber(), appUserInfo.getPhoneNumber());
    }

    @Test
    void shouldUpdateExistingAppUserInfo(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setInfoId(1);
        appUserInfo.setEmail("email");
        appUserInfo.setFirstName("fname");
        appUserInfo.setLastName("lname");
        appUserInfo.setAddress("address");
        appUserInfo.setPhoneNumber("pn");

        assertTrue(infoRepo.update(appUserInfo));
    }
    @Test
    void shouldNotUpdateNonExistingAppUserInfo(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setInfoId(111);
        appUserInfo.setEmail("email");
        appUserInfo.setFirstName("fname");
        appUserInfo.setLastName("lname");
        appUserInfo.setAddress("address");
        appUserInfo.setPhoneNumber("pn");

        assertFalse(infoRepo.update(appUserInfo));
    }
    @Test
    void shouldDeleteExistingId(){
        assertTrue(infoRepo.deleteById(2, 2));
    }
    @Test
    void shouldNotDeleteByNonExistingId(){
        assertFalse(infoRepo.deleteById(10, 1));
    }

}