package learn.resume.builder.data;

import learn.resume.builder.models.Skill;
import learn.resume.builder.models.WorkHistory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WorkHistoryRepository {
    List<WorkHistory> findAll();

    List<WorkHistory> getWorkHistoryByResumeId(int resumeId);
    WorkHistory add(WorkHistory workHistory);
    @Transactional
    boolean deleteById(int workHistoryId);

    boolean update(WorkHistory workHistory);
}
