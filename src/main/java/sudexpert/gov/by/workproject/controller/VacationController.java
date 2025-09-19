package sudexpert.gov.by.workproject.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.VacationDTO;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;
import sudexpert.gov.by.workproject.service.VacationService;
import sudexpert.gov.by.workproject.swagger.VacationAPI;

import java.util.List;

@RestController
@RequestMapping("api/vacation")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VacationController implements VacationAPI {

    VacationService vacationService;
    ServerProperties serverProperties;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public VacationDTO createVacation(@RequestBody @Validated(OnCreate.class) VacationDTO vacationDTO) {
        return vacationService.createVacation(vacationDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public VacationDTO updateVacation(
            @PathVariable Long id,
            @RequestBody @Validated(OnUpdate.class) VacationDTO vacationDTO) {
        return vacationService.updateVacation(id, vacationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void deleteVacation(@PathVariable Long id) {
        vacationService.deleteVacation(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public VacationDTO getVacationById(@PathVariable Long id) {
        return vacationService.getVacationById(id);
    }

    @GetMapping("/worker/{workerId}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<VacationDTO> getVacationsByWorkerId(@PathVariable Long workerId) {
        return vacationService.getVacationsByWorkerId(workerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<VacationDTO> getAllVacations() {
        return vacationService.getAllVacations();
    }
}
