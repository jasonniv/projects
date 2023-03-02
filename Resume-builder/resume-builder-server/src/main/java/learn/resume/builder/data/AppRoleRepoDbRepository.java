package learn.resume.builder.data;

import learn.resume.builder.data.mapper.AppRoleMapper;
import learn.resume.builder.data.mapper.AppUserMapper;
import learn.resume.builder.models.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppRoleRepoDbRepository implements AppRoleRepo{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public AppRole findByName(String roleName) {
        String sql = "select * from app_role where role_name = ?;";

        List<AppRole> roles = jdbcTemplate.query(sql,new AppRoleMapper(), roleName);
        if (roles.size() < 1){
            return null;
        }

        return roles.get(0);
    }
}
