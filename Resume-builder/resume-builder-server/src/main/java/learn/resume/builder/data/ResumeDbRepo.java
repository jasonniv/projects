package learn.resume.builder.data;


import learn.resume.builder.data.mapper.ResumeMapper;
import learn.resume.builder.models.Education;
import learn.resume.builder.models.Resume;
import learn.resume.builder.models.Skill;
import learn.resume.builder.models.WorkHistory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ResumeDbRepo implements ResumeRepo{

    private final JdbcTemplate jdbcTemplate;

    public ResumeDbRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Resume getByResumeId(int resumeId) {
        return jdbcTemplate.query("select * from resume_app inner join app_user_info on app_user_info.info_id = resume_app.info_id where resume_id = ?;", new ResumeMapper(), resumeId)
                .stream().findFirst().orElse(null);
    }

    @Override
    public List<Resume> getResumesByUser(int userId) {

        String sql = "select * from resume_app inner join app_user on app_user.user_id = resume_app.user_id inner join app_user_info on app_user_info.info_id = resume_app.info_id where app_user.user_id = ?;";
        List<Resume> resumeList = jdbcTemplate.query(sql, new ResumeMapper(), userId);
        if (resumeList.size() < 1){
            return null;
        }
        return resumeList;
    }

    @Override
    @Transactional
    public Resume add(Resume resumeToAdd) {

        if(resumeToAdd.getTemplateId()<1){
            return null;
        }
        if(resumeToAdd.getUserInfo() == null || resumeToAdd.getUser() == null){
            return null;
        }

        final String sql = "insert into resume_app (resume_name, template_id, info_id, user_id) values (?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, resumeToAdd.getResumeName());
            ps.setInt(2, resumeToAdd.getTemplateId());
            ps.setInt(3, resumeToAdd.getUserInfo().getInfoId());
            ps.setInt(4,resumeToAdd.getUser().getUserId());
            return ps;
        }, keyHolder);
        if (rowsAffected <= 0){
            return null;
        }
        resumeToAdd.setResumeId(keyHolder.getKey().intValue());
        setResumeSkill(resumeToAdd);
        setResumeEducation(resumeToAdd);
        setResumeWorkHistory(resumeToAdd);
        return resumeToAdd;
    }

    private void setResumeWorkHistory(Resume resumeToAdd) {
        for(WorkHistory workHistory : resumeToAdd.getWorkHistories()){
            jdbcTemplate.update("insert into resume_work_history (resume_id, work_history_id) values (?,?)", resumeToAdd.getResumeId(), workHistory.getWorkHistoryId());
        }
    }

    private void setResumeEducation(Resume resumeToAdd) {
        for(Education education : resumeToAdd.getEducations()){
            jdbcTemplate.update("insert into resume_education (resume_id, education_id) values (?,?)", resumeToAdd.getResumeId(), education.getEducationId());
        }

    }

    private void setResumeSkill(Resume resumeToAdd) {
        for(Skill skill : resumeToAdd.getSkills()){
            jdbcTemplate.update("insert into resume_skill (resume_id, skill_id) values (?,?)", resumeToAdd.getResumeId(), skill.getSkillId());
        }

    }

    @Override
    @Transactional
    public boolean deleteByResumeId(int resumeId) {
        jdbcTemplate.update("delete from resume_skill where resume_id = ?;", resumeId);
        jdbcTemplate.update("delete from resume_work_history where resume_id = ?;", resumeId);
        jdbcTemplate.update("delete from resume_education where resume_id = ?;", resumeId);
        return jdbcTemplate.update("delete from resume_app where resume_id = ?;", resumeId) > 0;
    }

    @Override
    @Transactional
    public boolean update(Resume resume) {
        updateResumeWorkHistory(resume);
        updateResumeSkill(resume);
        updateResumeEducation(resume);
        return jdbcTemplate.update("update resume_app set template_id = ?, resume_name=? where resume_id = ?;",
                resume.getTemplateId(), resume.getResumeName(), resume.getResumeId()) > 0;
    }

    private void updateResumeEducation(Resume resume) {
        jdbcTemplate.update("delete from resume_education where resume_id = ?;", resume.getResumeId());
        for(Education education : resume.getEducations()){
            jdbcTemplate.update("insert into resume_education (resume_id, education_id) values (?,?);", resume.getResumeId(), education.getEducationId());
        }
    }

    private void updateResumeSkill(Resume resume) {
        for(Skill skill : resume.getSkills()){
            jdbcTemplate.update("delete from resume_skill where resume_id = ?;", resume.getResumeId());
            jdbcTemplate.update("insert into resume_skill (resume_id,skill_id) values (?,?);", resume.getResumeId(), skill.getSkillId());
        }
    }

    private void updateResumeWorkHistory(Resume resume) {
        jdbcTemplate.update("delete from resume_work_history where resume_id = ?;", resume.getResumeId());

        for(WorkHistory workHistory : resume.getWorkHistories()){
            jdbcTemplate.update("insert into resume_work_history (resume_id, work_history_id) values (?,?);", resume.getResumeId(), workHistory.getWorkHistoryId());
        }
    }


}
