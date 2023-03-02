package learn.resume.builder.data.mapper;

import learn.resume.builder.models.AppUserInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUserInfoMapper implements RowMapper<AppUserInfo> {
    @Override
    public AppUserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setInfoId(rs.getInt("info_id"));
        appUserInfo.setEmail(rs.getString("email"));
        appUserInfo.setFirstName(rs.getString("first_name"));
        appUserInfo.setLastName(rs.getString("last_name"));
        appUserInfo.setAddress(rs.getString("address"));
        appUserInfo.setPhoneNumber(rs.getString("phone_number"));

        return appUserInfo;
    }
}
