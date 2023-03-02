package learn.resume.builder.data;

import learn.resume.builder.models.Resume;

import java.util.List;

public interface ResumeRepo {

    Resume getByResumeId(int resumeId);

    Resume add(Resume resumeToAdd);

    List<Resume> getResumesByUser(int userId);

    boolean deleteByResumeId(int resumeId);

    boolean update(Resume resume);
}
