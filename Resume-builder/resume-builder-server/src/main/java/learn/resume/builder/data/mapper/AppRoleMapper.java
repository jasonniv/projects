package learn.resume.builder.data.mapper;

import learn.resume.builder.models.AppRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppRoleMapper implements RowMapper<AppRole> {


    @Override
    public AppRole mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppRole role = new AppRole();

        role.setRoleId(rs.getInt("role_id"));
        role.setRoleName(rs.getString("role_name"));

        return role;
    }
}
