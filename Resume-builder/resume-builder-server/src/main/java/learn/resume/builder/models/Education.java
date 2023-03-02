package learn.resume.builder.models;

import java.util.List;

public class Education {
    private int educationId;
    private String schoolName;
    private String educationLevel;

    public Education(int educationId, String schoolName, String educationLevel) {
        this.educationId = educationId;
        this.schoolName = schoolName;
        this.educationLevel = educationLevel;
    }

    public Education(){

    }
    public int getEducationId() {
        return educationId;
    }

    public void setEducationId(int educationId) {
        this.educationId = educationId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }
}
