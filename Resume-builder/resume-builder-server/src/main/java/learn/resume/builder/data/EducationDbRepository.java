package learn.resume.builder.data;

import learn.resume.builder.data.mapper.EducationMapper;
import learn.resume.builder.models.Education;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class EducationDbRepository implements EducationRepo {
    private final JdbcTemplate jdbcTemplate;

    public EducationDbRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Education> findAll() {
        final String sql = "select education_id, school_name, education_level"
                + " from education;";
        return jdbcTemplate.query(sql, new EducationMapper());
    }

    @Override
    public List<Education> getEducationByResumeId(int resumeId) {
        return jdbcTemplate.query("select education.*\n" +
                "from education \n" +
                "inner join resume_education on education.education_id = resume_education.education_id\n" +
                "where resume_id = ?;", new EducationMapper(), resumeId);
    }
    @Override
    public Education add(Education education) {
        final String sql = "insert into education (school_name, education_level) values (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, education.getSchoolName());
            ps.setString(2, education.getEducationLevel());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        education.setEducationId(keyHolder.getKey().intValue());
        return education;
    }
    @Override
    public boolean update(Education education) {
        final String sql = "update education set\n" +
                "school_name = ?,\n" +
                "education_level = ?\n" +
                "where education_id = ?;";

        int rowsUpdated = jdbcTemplate.update(sql,
                education.getSchoolName(),
                education.getEducationLevel(),
                education.getEducationId());

        return rowsUpdated > 0;
    }
    @Override
    public boolean deleteById(int educationId) {
        jdbcTemplate.update("delete from resume_education where education_id = ?;"
        , educationId);
        return jdbcTemplate.update("delete from education where education_id = ?;"
        , educationId) > 0;
    }
}
