package learn.resume.builder.models;

import java.util.List;
import java.util.Objects;

public class Resume {

    private int resumeId;
    private int templateId;
    private String resumeName;
    private AppUser user;
    private AppUserInfo userInfo;
    List<Education> educations;
    List<Skill> skills;
    List<WorkHistory> workHistories;

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }
    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }
    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public AppUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(AppUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<WorkHistory> getWorkHistories() {
        return workHistories;
    }

    public void setWorkHistories(List<WorkHistory> workHistories) {
        this.workHistories = workHistories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return resumeId == resume.resumeId && templateId == resume.templateId && Objects.equals(educations, resume.educations) && Objects.equals(skills, resume.skills) && Objects.equals(workHistories, resume.workHistories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resumeId, templateId, educations, skills, workHistories);
    }
}
