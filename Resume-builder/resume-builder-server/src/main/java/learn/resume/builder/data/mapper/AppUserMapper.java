package learn.resume.builder.data.mapper;

import learn.resume.builder.models.AppRole;
import learn.resume.builder.models.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AppUserMapper implements RowMapper<AppUser> {
    private final List<AppRole> roles;

    public AppUserMapper(List<AppRole> roles) {
        this.roles = roles;
    }

    @Override
    public AppUser mapRow(ResultSet rs, int i) throws SQLException {
        return new AppUser(
                rs.getInt("user_id"),
                rs.getString("username"),
                rs.getString("password_hash"),
                rs.getBoolean("disabled"),
                roles);
    }
}
