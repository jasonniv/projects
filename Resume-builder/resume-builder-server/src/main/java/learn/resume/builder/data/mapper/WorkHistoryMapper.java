package learn.resume.builder.data.mapper;

import learn.resume.builder.models.WorkHistory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkHistoryMapper implements RowMapper<WorkHistory> {

    @Override
    public WorkHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
        WorkHistory workHistory = new WorkHistory();
        workHistory.setWorkHistoryId(rs.getInt("work_history_id"));
        workHistory.setCompany(rs.getString("work_history_company"));
        workHistory.setJobTitle(rs.getString("job_title"));
        workHistory.setStartDate(rs.getDate("start_date").toLocalDate());
        workHistory.setEndDate(rs.getDate("end_date").toLocalDate());
        workHistory.setJobDescription(rs.getString("job_description"));

        return workHistory;
    }
}
