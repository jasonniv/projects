package learn.resume.builder.data.mapper;

import learn.resume.builder.models.AppRole;
import learn.resume.builder.models.AppUser;
import learn.resume.builder.models.AppUserInfo;
import learn.resume.builder.models.Resume;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResumeMapper implements RowMapper<Resume> {

    @Override
    public Resume mapRow(ResultSet rs, int rowNum) throws SQLException {

        Resume resume = new Resume();
        resume.setResumeId(rs.getInt("resume_id"));
        resume.setResumeName(rs.getString("resume_name"));
        resume.setTemplateId(rs.getInt("template_id"));

        AppUserInfoMapper userInfoMapper = new AppUserInfoMapper();
        resume.setUserInfo(userInfoMapper.mapRow(rs, rowNum));


        return resume;
    }
}
