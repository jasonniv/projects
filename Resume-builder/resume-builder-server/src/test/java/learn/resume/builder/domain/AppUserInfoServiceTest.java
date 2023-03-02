package learn.resume.builder.domain;

import learn.resume.builder.data.AppUserInfoRepo;
import learn.resume.builder.models.AppUserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AppUserInfoServiceTest {
    @MockBean
    AppUserInfoRepo repository;

    @Autowired
    AppUserInfoService service;

    @Test
    void shouldFindAllAppUserInfo() {
        List<AppUserInfo> appUserInfo = new ArrayList<>();
        appUserInfo.add(new AppUserInfo(1, "email", "fname", "lname", "address",
                "pn"));
        appUserInfo.add(new AppUserInfo(2, "email2", "fname2", "lname2", "address2",
                "pn2"));

        when(repository.findAll()).thenReturn(appUserInfo);
        List<AppUserInfo> result = service.findAll();
        assertEquals(2, appUserInfo.size());
    }
    @Test
    void shouldAddAppUserInfo(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setEmail("email");
        appUserInfo.setFirstName("fname");
        appUserInfo.setLastName("lname");
        appUserInfo.setAddress("address");
        appUserInfo.setPhoneNumber("pn");

        Result<AppUserInfo> result = service.add(appUserInfo);
        assertTrue(result.isSuccess());
    }
    @Test
    void shouldNotAddAppUserInfoIfIdIsNotZero(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setInfoId(2);
        appUserInfo.setEmail("email");
        appUserInfo.setFirstName("fname");
        appUserInfo.setLastName("lname");
        appUserInfo.setAddress("address");
        appUserInfo.setPhoneNumber("pn");

        Result<AppUserInfo> result = service.add(appUserInfo);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddNullAppUserInfo(){
        Result<AppUserInfo> result = service.add(null);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddAppUserInfoIfEmailIsNull(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setEmail(null);
        appUserInfo.setFirstName("fname");
        appUserInfo.setLastName("lname");
        appUserInfo.setAddress("address");
        appUserInfo.setPhoneNumber("pn");

        Result<AppUserInfo> result = service.add(appUserInfo);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddAppUserInfoIfEmailIsBlank(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setEmail("");
        appUserInfo.setFirstName("fname");
        appUserInfo.setLastName("lname");
        appUserInfo.setAddress("address");
        appUserInfo.setPhoneNumber("pn");

        Result<AppUserInfo> result = service.add(appUserInfo);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddAppUserInfoIfFirstNameIsNull(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setEmail("email");
        appUserInfo.setFirstName(null);
        appUserInfo.setLastName("lname");
        appUserInfo.setAddress("address");
        appUserInfo.setPhoneNumber("pn");

        Result<AppUserInfo> result = service.add(appUserInfo);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddAppUserInfoIfFirstNameIsBlank(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setEmail("email");
        appUserInfo.setFirstName("");
        appUserInfo.setLastName("lname");
        appUserInfo.setAddress("address");
        appUserInfo.setPhoneNumber("pn");

        Result<AppUserInfo> result = service.add(appUserInfo);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddAppUserInfoIfLastNameIsNull(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setEmail("email");
        appUserInfo.setFirstName("fname");
        appUserInfo.setLastName(null);
        appUserInfo.setAddress("address");
        appUserInfo.setPhoneNumber("pn");

        Result<AppUserInfo> result = service.add(appUserInfo);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddAppUserInfoIfLastNameIsBlank(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setEmail("email");
        appUserInfo.setFirstName("fname");
        appUserInfo.setLastName("");
        appUserInfo.setAddress("address");
        appUserInfo.setPhoneNumber("pn");

        Result<AppUserInfo> result = service.add(appUserInfo);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddAppUserInfoIfPhoneNumberIsNull(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setEmail("email");
        appUserInfo.setFirstName("fname");
        appUserInfo.setLastName("lname");
        appUserInfo.setAddress("address");
        appUserInfo.setPhoneNumber(null);

        Result<AppUserInfo> result = service.add(appUserInfo);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldNotAddAppUserInfoIfPhoneNumberIsBlank(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setEmail("email");
        appUserInfo.setFirstName("fname");
        appUserInfo.setLastName("lname");
        appUserInfo.setAddress("address");
        appUserInfo.setPhoneNumber("");

        Result<AppUserInfo> result = service.add(appUserInfo);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }
    @Test
    void shouldAddAppUserInfoIfAddressIsNull(){
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setEmail("email");
        appUserInfo.setFirstName("fname");
        appUserInfo.setLastName("lname");
        appUserInfo.setAddress(null);
        appUserInfo.setPhoneNumber("pn");

        Result<AppUserInfo> result = service.add(appUserInfo);
        assertTrue(result.isSuccess());
    }
    @Test
    void shouldNotDeleteNonExistentId() {
        when(repository.deleteById(11, 100)).thenReturn(false);

        Result<AppUserInfo> result = service.deleteById(1,100);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldDeleteId(){
        when(repository.deleteById(1,1)).thenReturn(true);

        Result<AppUserInfo> result = service.deleteById(1,1);
        assertTrue(result.isSuccess());
    }
    @Test
    void shouldUpdate() {
        AppUserInfo appUserInfo = new AppUserInfo(1, "email", "fname",
                "lastname", "address", "pn");
        when(repository.update(appUserInfo)).thenReturn(true);

        Result<AppUserInfo> result = service.update(appUserInfo);
        assertTrue(result.isSuccess());
    }
    @Test
    void shouldNotUpdateNullEmail() {
        AppUserInfo appUserInfo = new AppUserInfo(1, null, "fname",
                "lastname", "address", "pn");
        when(repository.update(appUserInfo)).thenReturn(false);

        Result<AppUserInfo> result = service.update(appUserInfo);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldNotUpdateBlankEmail() {
        AppUserInfo appUserInfo = new AppUserInfo(1, "", "fname",
                "lastname", "address", "pn");
        when(repository.update(appUserInfo)).thenReturn(false);

        Result<AppUserInfo> result = service.update(appUserInfo);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldNotUpdateNullFirstName() {
        AppUserInfo appUserInfo = new AppUserInfo(1, "email", null,
                "lastname", "address", "pn");
        when(repository.update(appUserInfo)).thenReturn(false);

        Result<AppUserInfo> result = service.update(appUserInfo);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldNotUpdateBlankFirstName() {
        AppUserInfo appUserInfo = new AppUserInfo(1, "email", "",
                "lastname", "address", "pn");
        when(repository.update(appUserInfo)).thenReturn(false);

        Result<AppUserInfo> result = service.update(appUserInfo);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldNotUpdateNullLastName() {
        AppUserInfo appUserInfo = new AppUserInfo(1, "email", "fname",
                null, "address", "pn");
        when(repository.update(appUserInfo)).thenReturn(false);

        Result<AppUserInfo> result = service.update(appUserInfo);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldNotUpdateBlankLastName() {
        AppUserInfo appUserInfo = new AppUserInfo(1, "email", "fname",
                "", "address", "pn");
        when(repository.update(appUserInfo)).thenReturn(false);

        Result<AppUserInfo> result = service.update(appUserInfo);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldNotUpdateNullPhoneNumber() {
        AppUserInfo appUserInfo = new AppUserInfo(1, "email", "fname",
                "lname", "address", null);
        when(repository.update(appUserInfo)).thenReturn(false);

        Result<AppUserInfo> result = service.update(appUserInfo);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldNotUpdateBlankPhoneNumber() {
        AppUserInfo appUserInfo = new AppUserInfo(1, "email", "fname",
                "lname", "address", "");
        when(repository.update(appUserInfo)).thenReturn(false);

        Result<AppUserInfo> result = service.update(appUserInfo);
        assertFalse(result.isSuccess());
    }
    @Test
    void shouldNotUpdate() {
        AppUserInfo appUserInfo = new AppUserInfo(1, "email", "fname",
                "lastname", "address", "pn");
        when(repository.update(appUserInfo)).thenReturn(false);

        Result<AppUserInfo> result = service.update(appUserInfo);
        assertFalse(result.isSuccess());
    }
}