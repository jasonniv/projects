package learn.resume.builder.models;

public class ResumeSkill {
    private int resumeId;
    private int skillId;

    public ResumeSkill(int resumeId, int skillId) {
        this.resumeId = resumeId;
        this.skillId = skillId;
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }
}
