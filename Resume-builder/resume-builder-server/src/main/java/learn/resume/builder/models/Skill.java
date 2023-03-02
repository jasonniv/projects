package learn.resume.builder.models;

import java.util.List;

public class Skill {
    private int skillId;
    private String skillName;


    public Skill(int skillId, String skillName) {
        this.skillId = skillId;
        this.skillName = skillName;
    }
    public Skill(){

    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

}
