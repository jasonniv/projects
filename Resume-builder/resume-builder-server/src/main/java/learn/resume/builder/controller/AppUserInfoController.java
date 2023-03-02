package learn.resume.builder.controller;

import learn.resume.builder.App;
import learn.resume.builder.domain.AppUserInfoService;
import learn.resume.builder.domain.Result;
import learn.resume.builder.domain.ResultType;
import learn.resume.builder.models.AppUserInfo;
import learn.resume.builder.models.Education;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/appUserInfo")
public class AppUserInfoController {
    @Autowired
    private final AppUserInfoService service;
    public AppUserInfoController(AppUserInfoService service) {
        this.service = service;
    }
    @GetMapping
    public List<AppUserInfo> findAll() {
        return service.findAll();
    }
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody AppUserInfo appUserInfo){
        Result<AppUserInfo> result = service.add(appUserInfo);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }
    @PutMapping("/{appUserInfoId}")
    public ResponseEntity<Object> update
            (@PathVariable int appUserInfoId, @RequestBody AppUserInfo appUserInfo) {
        if (appUserInfoId != appUserInfo.getInfoId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<AppUserInfo> result = service.update(appUserInfo);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }
    @DeleteMapping("/{appUserInfoId}")
    public ResponseEntity deleteById(@PathVariable int appUserInfoId, @PathVariable int resumeId) {
        Result<AppUserInfo> result = service.deleteById(appUserInfoId, resumeId);
        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if(result.getType() == ResultType.NOT_FOUND){
            return new ResponseEntity<>(result.getMessages(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
    }
}
