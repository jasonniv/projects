package learn.resume.builder.controller;

import learn.resume.builder.domain.Result;
import learn.resume.builder.domain.ResultType;
import learn.resume.builder.domain.SkillService;
import learn.resume.builder.models.Education;
import learn.resume.builder.models.Skill;
import learn.resume.builder.models.WorkHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/skill")
public class SkillController {

    @Autowired
    private final SkillService service;

    public SkillController(SkillService service) {
        this.service = service;
    }
    @GetMapping
    public List<Skill> findAll() {
        return service.findAll();
    }
//    @PostMapping
//    public ResponseEntity<Object> add(@RequestBody Skill skill){
//        Result<Skill> result = service.add(skill);
//        if (result.isSuccess()) {
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        }
//        return ErrorResponse.build(result);
//    }
    @DeleteMapping("/{skillId}")
    public ResponseEntity deleteById(@PathVariable int skillId) {
        Result<Skill> result = service.deleteById(skillId);
        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if(result.getType() == ResultType.NOT_FOUND){
            return new ResponseEntity<>(result.getMessages(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{skillId}")
    public ResponseEntity updateById(@PathVariable int skillId, @RequestBody Skill skill){
        if (skillId != skill.getSkillId()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Skill> result = service.update(skill);

        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ErrorResponse.build(result);
    }


}
