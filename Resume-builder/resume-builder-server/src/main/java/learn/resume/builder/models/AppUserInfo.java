package learn.resume.builder.models;

public class AppUserInfo {
    private int infoId;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private Resume resume;

    public AppUserInfo(int infoId, String email, String firstName, String lastName, String address, String phoneNumber) {
        this.infoId = infoId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public AppUserInfo(){

    }
    public int getInfoId() {
        return infoId;
    }
    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Resume getResume() {
        return resume;
    }
    public void setResume(Resume resume) {
        this.resume = resume;
    }

}
