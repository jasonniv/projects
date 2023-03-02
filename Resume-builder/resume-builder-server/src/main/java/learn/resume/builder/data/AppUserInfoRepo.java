package learn.resume.builder.data;

import learn.resume.builder.models.AppUserInfo;
import learn.resume.builder.models.Education;
import learn.resume.builder.models.Skill;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AppUserInfoRepo {

    List<AppUserInfo> findAll();

    AppUserInfo findUserByResumeId(int resumeId);

    AppUserInfo add(AppUserInfo appUserInfo);
    boolean update(AppUserInfo appUserInfo);
    @Transactional
    boolean deleteById(int appUserInfoId, int resumeId);
}
