package learn.resume.builder.domain;

import learn.resume.builder.data.EducationRepo;
import learn.resume.builder.models.Education;
import learn.resume.builder.models.Resume;
import learn.resume.builder.models.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {
    @Autowired
    private final EducationRepo repository;
    public EducationService(EducationRepo repository) {
        this.repository = repository;
    }
    public List<Education> findAll() {
        return repository.findAll();
    }

    public Result<Education> add (Education education){
        Result<Education> result = validate(education);
        if (!result.isSuccess()) {
            return result;
        }
        if (education.getEducationId() != 0) {
            result.addMessage("educationId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }
        education = repository.add(education);
        result.setPayload(education);
        return result;
    }

    public Result<Education> update(Education education) {
        Result<Education> result = validate(education);
        if(result.getType() != ResultType.SUCCESS){
            return result;
        }
        if (education.getEducationId() <= 0){
            result.addMessage("Education Id is required", ResultType.INVALID);
        }
        if(result.isSuccess()){
            if(repository.update(education)){
                result.setPayload(education);
            } else {
                result.addMessage("Education Id was not found", ResultType.NOT_FOUND);
            }
        }
        return result;
    }

    public Result deleteById(int educationId) {
        Result<Education> result = new Result<>();
        if (!repository.deleteById(educationId)) {
            result.addMessage("Education Id Id was not found", ResultType.NOT_FOUND);
        }
        if(result.isSuccess()){
            repository.deleteById(educationId);
        }
        return result;
    }

    public Result<List<Education>> addEducationFromResume(Resume resume) {
        Result<List<Education>> result = new Result<>();
        List<Education> educations = resume.getEducations();

        if(resume == null){
            result.addMessage("Resume is null", ResultType.INVALID);
            return result;
        }

        if(educations == null){
            result.addMessage("Education is null", ResultType.INVALID);
            return result;
        }

        for(Education education : educations){
            Result<Education> educationResult = validate(education);
            if (!educationResult.isSuccess()){
                result.addMessage("Education could not be added", educationResult.getType());
                return result;
            }
            repository.add(education);
        }
        result.setPayload(educations);
        return result;
    }

    public Result<List<Education>> updateEducationFromResume(Resume resume) {
        Result<List<Education>> result = new Result<>();
        List<Education> educations = resume.getEducations();

        if(resume == null){
            result.addMessage("Resume is null", ResultType.INVALID);
            return result;
        }

        if (educations == null){
            return result;
        }

        for(Education education : educations){
            Result<Education> educationResult = validate(education);
            if (!educationResult.isSuccess()){
                result.addMessage("Skills could not be updated", educationResult.getType());
                return result;
            }
            repository.update(education);
        }
        result.setPayload(educations);
        return result;
    }

    private Result<Education> validate(Education education) {
        Result<Education> result = new Result<>();
        if (education == null) {
            result.addMessage("education cannot be null", ResultType.INVALID);
            return result;
        }
        if (education.getSchoolName() == null || education.getSchoolName().isBlank()){
            result.addMessage("school name is required", ResultType.INVALID);
        }
        if (education.getEducationLevel() == null || education.getEducationLevel().isBlank()){
            result.addMessage("education level is required", ResultType.INVALID);
        }
        return result;
    }


}
