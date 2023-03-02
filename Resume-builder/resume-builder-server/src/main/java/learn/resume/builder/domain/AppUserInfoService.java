package learn.resume.builder.domain;

import learn.resume.builder.data.AppUserInfoRepo;
import learn.resume.builder.models.AppUserInfo;
import learn.resume.builder.models.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserInfoService {
    @Autowired
    private final AppUserInfoRepo repository;

    public AppUserInfoService(AppUserInfoRepo repository) {
        this.repository = repository;
    }

    public List<AppUserInfo> findAll() {
        return repository.findAll();
    }

    public Result<AppUserInfo> add (AppUserInfo appUserInfo){
        Result<AppUserInfo> result = validate(appUserInfo);
        if (!result.isSuccess()) {
            return result;
        }
        if (appUserInfo.getInfoId() != 0) {
            result.addMessage("appUserInfoId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }
        appUserInfo = repository.add(appUserInfo);
        result.setPayload(appUserInfo);
        return result;
    }
    public Result<AppUserInfo> update(AppUserInfo appUserInfo) {
        Result<AppUserInfo> result = validate(appUserInfo);
        if(result.getType() != ResultType.SUCCESS){
            return result;
        }
        if (appUserInfo.getInfoId() <= 0){
            result.addMessage("Info Id is required", ResultType.INVALID);
        }
        if(result.isSuccess()){
            if(repository.update(appUserInfo)){
                result.setPayload(appUserInfo);
            } else {
                result.addMessage("Info Id was not found", ResultType.NOT_FOUND);
            }
        }
        return result;
    }
    public Result deleteById(int appUserInfoId, int resumeId) {
        Result<AppUserInfo> result = new Result<>();
        if (!repository.deleteById(appUserInfoId, resumeId)) {
            result.addMessage("App User Info Id Id was not found", ResultType.NOT_FOUND);
        }
        if(result.isSuccess()){
            repository.deleteById(appUserInfoId, resumeId);
        }
        return result;
    }
    private Result<AppUserInfo> validate(AppUserInfo appUserInfo) {
        Result<AppUserInfo> result = new Result<>();
        if (appUserInfo == null) {
            result.addMessage("appUserInfo cannot be null", ResultType.INVALID);
            return result;
        }
        if (appUserInfo.getEmail() == null || appUserInfo.getEmail().isBlank()){
            result.addMessage("Email is required", ResultType.INVALID);
        }
        if (appUserInfo.getFirstName() == null || appUserInfo.getFirstName().isBlank()){
            result.addMessage("First Name is required", ResultType.INVALID);
        }
        if (appUserInfo.getLastName() == null || appUserInfo.getLastName().isBlank()){
            result.addMessage("Last Name is required", ResultType.INVALID);
        }
        if (appUserInfo.getPhoneNumber() == null || appUserInfo.getPhoneNumber().isBlank()){
            result.addMessage("Phone Number is required", ResultType.INVALID);
        }

        return result;
    }

}
