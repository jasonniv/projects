package learn.resume.builder.data;

import learn.resume.builder.data.mapper.SkillMapper;
import learn.resume.builder.models.Resume;
import learn.resume.builder.models.Skill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class SkillDbRepository implements SkillRepo {

    private final JdbcTemplate jdbcTemplate;

    public SkillDbRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Skill> findAll() {
        final String sql = "select skill_id, skill_name from skill;";

        return jdbcTemplate.query(sql, new SkillMapper());
    }

    @Override
    public List<Skill> getSkillByResumeId(int resumeId) {
        return jdbcTemplate.query("select skill.*\n" +
                "from skill \n" +
                "inner join resume_skill on skill.skill_id = resume_skill.skill_id\n" +
                "where resume_id = ?;", new SkillMapper(), resumeId);
    }

    @Override
    public Skill add(Skill skill) {

        final String sql = "insert into skill (skill_name) values (?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, skill.getSkillName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        skill.setSkillId(keyHolder.getKey().intValue());

        return skill;
    }

    @Override
    public boolean deleteById(int skillId) {
        jdbcTemplate.update("delete from resume_skill where skill_id = ?;"
            , skillId);
        return jdbcTemplate.update("delete from skill where skill_id = ?; "
                , skillId) > 0;
    }

    @Override
    public boolean update(Skill skill) {
        final String sql = " update skill set skill_name = ? where skill_id = ?;";

        return jdbcTemplate.update(sql, skill.getSkillName(), skill.getSkillId()) > 0;
    }

}
