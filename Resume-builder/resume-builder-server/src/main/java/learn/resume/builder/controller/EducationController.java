package learn.resume.builder.controller;

import learn.resume.builder.domain.EducationService;
import learn.resume.builder.domain.Result;
import learn.resume.builder.domain.ResultType;
import learn.resume.builder.models.Education;
import learn.resume.builder.models.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/education")
public class EducationController {

    @Autowired
    private final EducationService service;

    public EducationController(EducationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Education> findAll() {
        return service.findAll();
    }
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Education education){
        Result<Education> result = service.add(education);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }
    @PutMapping("/{educationId}")
    public ResponseEntity<Object> update
            (@PathVariable int educationId, @RequestBody Education education) {
        if (educationId != education.getEducationId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Education> result = service.update(education);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }
    @DeleteMapping("/{educationId}")
    public ResponseEntity deleteById(@PathVariable int educationId) {
        Result<Education> result = service.deleteById(educationId);
        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if(result.getType() == ResultType.NOT_FOUND){
            return new ResponseEntity<>(result.getMessages(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
    }

}
