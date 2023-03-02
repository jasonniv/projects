package learn.resume.builder.controller;

import learn.resume.builder.domain.*;
import learn.resume.builder.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final WorkHistoryService workHistoryService;
    private final EducationService educationService;
    private final SkillService skillService;
    private final AppUserInfoService appUserInfoService;

    public ResumeController(ResumeService service, WorkHistoryService workHistoryService, EducationService educationService, SkillService skillService, AppUserInfoService appUserInfoService) {
        this.resumeService = service;
        this.workHistoryService = workHistoryService;
        this.educationService = educationService;
        this.skillService = skillService;
        this.appUserInfoService = appUserInfoService;
    }



    @GetMapping("/user")
    public ResponseEntity getResumeByUserId(){
        AppUser currentUser = (AppUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        boolean jobSeeker = currentUser.getUserRoles()
                .stream().anyMatch(r->r.getRoleName().equals("Job Seeker"));


        Result<List<Resume>> getResult = resumeService.getResumesByUser(currentUser.getUserId());
        if(getResult.isSuccess()){
            List<Resume> userResumes = getResult.getPayload();

            return ResponseEntity.ok(userResumes);
        } else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/{resumeId}")
    public ResponseEntity<Resume> findByResumeId(@PathVariable int resumeId){
        Resume resume = resumeService.findByResumeId(resumeId);

        if (resume == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(resume);
    }


    @PostMapping
    public ResponseEntity<Object> addResume(@RequestBody Resume resumeToAdd){

        AppUser currentUser = (AppUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        boolean jobSeeker = currentUser.getUserRoles()
                .stream().anyMatch(r->r.getRoleName().equals("Job Seeker"));

        if (jobSeeker) {
            resumeToAdd.setUser(currentUser);
        }

        Result<List<WorkHistory>> workHistoriesResult = workHistoryService.addWorkHistoryFromResume(resumeToAdd);
        if(!workHistoriesResult.isSuccess()){
            return ErrorResponse.build(workHistoriesResult);
        }

        Result<List<Education>> educationsResult = educationService.addEducationFromResume(resumeToAdd);
        if(!educationsResult.isSuccess()){
            return ErrorResponse.build(educationsResult);
        }

        Result<List<Skill>> skillsResult = skillService.addSkillFromResume(resumeToAdd);
        if(!skillsResult.isSuccess()){
            return ErrorResponse.build(skillsResult);
        }

        Result<AppUserInfo> appUserInfoResult = appUserInfoService.add(resumeToAdd.getUserInfo());
        if(!appUserInfoResult.isSuccess()){
            return ErrorResponse.build(appUserInfoResult);
        }
        resumeToAdd.setWorkHistories(workHistoriesResult.getPayload());
        resumeToAdd.setEducations(educationsResult.getPayload());
        resumeToAdd.setSkills(skillsResult.getPayload());
        resumeToAdd.setUserInfo(appUserInfoResult.getPayload());

        Result<Resume> resumeResult = resumeService.addResume(resumeToAdd);
        if (!resumeResult.isSuccess()) {
            return ErrorResponse.build(resumeResult);
        }


        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity deleteById(@PathVariable int resumeId){
        Result<Resume> result = resumeService.deleteByResumeId(resumeId);

        if (result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{resumeId}")
    public ResponseEntity editResume(@PathVariable int resumeId, @RequestBody Resume resume){
        AppUser currentUser = (AppUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        boolean jobSeeker = currentUser.getUserRoles()
                .stream().anyMatch(r->r.getRoleName().equals("Job Seeker"));

        if (jobSeeker) {
            resume.setUser(currentUser);
        }



        if (resumeId != resume.getResumeId()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Result<List<WorkHistory>> workHistoriesResult = workHistoryService.updateWorkHistoryFromResume(resume);
        if(!workHistoriesResult.isSuccess()){
            return ErrorResponse.build(workHistoriesResult);
        }

        Result<List<Education>> educationsResult = educationService.updateEducationFromResume(resume);
        if(!educationsResult.isSuccess()){
            return ErrorResponse.build(educationsResult);
        }

        Result<List<Skill>> skillsResult = skillService.updateSkillFromResume(resume);
        if(!skillsResult.isSuccess()){
            return ErrorResponse.build(skillsResult);
        }

        Result<AppUserInfo> appUserInfoResult = appUserInfoService.update(resume.getUserInfo());
        if(!appUserInfoResult.isSuccess()){
            return ErrorResponse.build(appUserInfoResult);
        }

        resume.setWorkHistories(workHistoriesResult.getPayload());
        resume.setEducations(educationsResult.getPayload());
        resume.setSkills(skillsResult.getPayload());
        resume.setUserInfo(appUserInfoResult.getPayload());

        Result<Resume> resumeResult = resumeService.updateResume(resume);
        if(!resumeResult.isSuccess()) {
            return ErrorResponse.build(resumeResult);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
