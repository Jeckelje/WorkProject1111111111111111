package sudexpert.gov.by.workproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.QualificationDTO;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;
import sudexpert.gov.by.workproject.service.QualificationService;
import sudexpert.gov.by.workproject.swagger.QualificationAPI;

import java.util.List;

@RestController
@RequestMapping("api/qualification")
@RequiredArgsConstructor
@Validated
public class QualificationController implements QualificationAPI {

    private final QualificationService qualificationService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public QualificationDTO createQualification(@RequestBody @Validated(OnCreate.class) QualificationDTO qualificationDTO) {
        return qualificationService.createQualification(qualificationDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public QualificationDTO updateQualification(
            @PathVariable Long id,
            @RequestBody @Validated(OnUpdate.class) QualificationDTO qualificationDTO) {
        return qualificationService.updateQualification(id, qualificationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void deleteQualification(@PathVariable Long id) {
        qualificationService.deleteQualification(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public QualificationDTO getQualificationById(@PathVariable Long id) {
        return qualificationService.getQualificationById(id);
    }

    @GetMapping("/worker/{workerId}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<QualificationDTO> getQualificationsByWorkerId(@PathVariable Long workerId) {
        return qualificationService.getQualificationsByWorkerId(workerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<QualificationDTO> getAllQualification() {
        return qualificationService.getAllQualifications();
    }
}
