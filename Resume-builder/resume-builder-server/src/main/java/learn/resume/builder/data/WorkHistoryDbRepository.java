package learn.resume.builder.data;

import learn.resume.builder.data.mapper.WorkHistoryMapper;
import learn.resume.builder.models.WorkHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


@Repository
public class WorkHistoryDbRepository implements WorkHistoryRepository{

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public WorkHistoryDbRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<WorkHistory> findAll() {
        final String sql = "select work_history_id, job_title, start_date, end_date, job_description\n" +
                "from work_history;";
        return jdbcTemplate.query(sql, new WorkHistoryMapper());
    }

    @Override
    public List<WorkHistory> getWorkHistoryByResumeId(int resumeId) {
        return jdbcTemplate.query("select work_history.*\n" +
                "from work_history \n" +
                "inner join resume_work_history on work_history.work_history_id = resume_work_history.work_history_id\n" +
                "where resume_id = ?;", new WorkHistoryMapper(), resumeId);
    }
    @Override
    public WorkHistory add(WorkHistory workHistory) {

        final String sql = "insert into work_history (work_history_company, job_title, start_date, end_date, job_description) "
                + " values (?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, workHistory.getCompany());
            ps.setString(2, workHistory.getJobTitle());
            ps.setDate(3, Date.valueOf(workHistory.getStartDate()));
            ps.setDate(4, workHistory.getEndDate() == null ? null : Date.valueOf(workHistory.getEndDate()));
            ps.setString(5, workHistory.getJobDescription());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        workHistory.setWorkHistoryId(keyHolder.getKey().intValue());
        return workHistory;
    }

    @Override
    @Transactional
    public boolean deleteById(int workHistoryId) {
        // first delete from bridge table then work_history
        jdbcTemplate.update("delete from resume_work_history where work_history_id = ?; "
                , workHistoryId);
        return jdbcTemplate.update("delete from work_history where work_history_id = ?; "
                , workHistoryId) > 0;
    }

    @Override
    public boolean update(WorkHistory workHistory) {
       final String sql = " update work_history set job_title = ?, work_history_company = ?, start_date = ?, end_date = ?, job_description = ? where work_history_id = ?;";

       return jdbcTemplate.update(sql, workHistory.getJobTitle(),
               workHistory.getCompany(), workHistory.getStartDate(), workHistory.getEndDate(),
               workHistory.getJobDescription(), workHistory.getWorkHistoryId()) > 0;
    }
}
