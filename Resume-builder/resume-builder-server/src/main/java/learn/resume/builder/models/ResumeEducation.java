package learn.resume.builder.models;

public class ResumeEducation {
    private int educationId;
    private int resumeId;

    public ResumeEducation(int educationId, int resumeId) {
        this.educationId = educationId;
        this.resumeId = resumeId;
    }

    public int getEducationId() {
        return educationId;
    }

    public void setEducationId(int educationId) {
        this.educationId = educationId;
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }
}
