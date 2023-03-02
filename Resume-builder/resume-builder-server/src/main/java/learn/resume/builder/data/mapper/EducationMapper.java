package learn.resume.builder.data.mapper;

import learn.resume.builder.models.Education;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EducationMapper implements RowMapper<Education> {

    @Override
    public Education mapRow(ResultSet rs, int rowNum) throws SQLException {
        Education education = new Education();
        education.setEducationId(rs.getInt("education_id"));
        education.setSchoolName(rs.getString("school_name"));
        education.setEducationLevel(rs.getString("education_level"));

        return education;
    }
}
