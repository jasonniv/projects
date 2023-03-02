package learn.resume.builder.data;

import learn.resume.builder.data.mapper.AppRoleMapper;
import learn.resume.builder.data.mapper.AppUserMapper;
import learn.resume.builder.models.AppRole;
import learn.resume.builder.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@Repository
public class AppUserRepo {
    private final JdbcTemplate jdbcTemplate;

    public AppUserRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AppUser findByUsername(String username) {
        List<AppRole> roles = getRolesByUsername(username);
        final String sql = "select user_id, username, password_hash, disabled"
                + " from app_user"
                + " where username = ? ;";

        return jdbcTemplate.query(sql, new AppUserMapper(roles), username)
                .stream().findFirst().orElse(null);
    }
    @Transactional
    public AppUser create(AppUser user) {
        final String sql = "insert into app_user (username, password_hash) values (?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        user.setUserId(keyHolder.getKey().intValue());
        updateRoles(user);

        return user;
    }
    @Transactional
    public void update(AppUser user) {
        final String sql = "update app_user set"
                + " username = ?, disabled = ?"
                + " where user_id = ?;";
        jdbcTemplate.update(sql,
                user.getUsername(), !user.isEnabled(), user.getUserId());

        updateRoles(user);
    }
    private void updateRoles(AppUser user) {
        jdbcTemplate.update("delete from app_user_role where user_id = ?;", user.getUserId());
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if (authorities == null) {
            return;
        }
        for (String role : AppUser.convertAuthoritiesToRoles(authorities)) {
            String sql= "insert into app_user_role (user_id, role_id)"
                    + " select ?, role_id from app_role where role_name = ?;";
            jdbcTemplate.update(sql, user.getUserId(), role);
        }
    }

    private List<AppRole> getRolesByUsername(String username) {
        final String sql = "select r.role_name, r.role_id"
                + " from app_user_role ur"
                + " inner join app_role r on ur.role_id = r.role_id"
                + " inner join app_user au on ur.user_id = au.user_id"
                + " where au.username = ?;";
        return jdbcTemplate.query(sql, new AppRoleMapper(), username);
    }
}
