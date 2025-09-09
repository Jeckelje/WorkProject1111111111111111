package sudexpert.gov.by.workproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.ECCDTO;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;
import sudexpert.gov.by.workproject.service.ECCService;
import sudexpert.gov.by.workproject.swagger.ECCAPI;

import java.util.List;

@RestController
@RequestMapping("api/ecc")
@RequiredArgsConstructor
@Validated
public class ECCController implements ECCAPI {

    private final ECCService eccService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ECCDTO createEcc(@RequestBody @Validated(OnCreate.class) ECCDTO eccdto) {
        return eccService.createEcc(eccdto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ECCDTO updateEcc(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) ECCDTO eccdto) {
        return eccService.updateEcc(id, eccdto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void deleteEcc(@PathVariable Long id) {
        eccService.deleteEcc(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ECCDTO getEccById(@PathVariable Long id) {
        return eccService.getEccById(id);
    }

    @GetMapping("/worker/{workerId}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<ECCDTO> getECCSByWorkerId(@PathVariable Long workerId) {
        return eccService.getEccsByWorkerId(workerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<ECCDTO> getAllECCS() {
        return eccService.getAllEcc();
    }
}
