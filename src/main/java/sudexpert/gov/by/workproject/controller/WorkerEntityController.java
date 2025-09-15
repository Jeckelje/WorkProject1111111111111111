package sudexpert.gov.by.workproject.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;
import sudexpert.gov.by.workproject.service.WorkerEntityService;
import sudexpert.gov.by.workproject.swagger.WorkerEntityAPI;

import java.util.List;

@RestController
@RequestMapping("api/workers")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkerEntityController implements WorkerEntityAPI {

    WorkerEntityService workerEntityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public WorkerEntityDTO createWorker(@RequestBody @Validated(OnCreate.class) WorkerEntityDTO workerEntityDTO) {
        return workerEntityService.createWorkerEntity(workerEntityDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public WorkerEntityDTO updateWorker(@PathVariable Long id,
                                        @RequestBody @Validated(OnUpdate.class) WorkerEntityDTO workerEntityDTO) {
        return workerEntityService.updateWorkerEntity(id, workerEntityDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteWorker(@PathVariable Long id) {
        workerEntityService.deleteWorkerEntity(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public WorkerEntityDTO getWorkerById(@PathVariable Long id) {
        return workerEntityService.getWorkerEntityById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<WorkerEntityDTO> getAllWorkers() {
        return workerEntityService.getAllWorkers();
    }

    // Методы для флагов
    @PatchMapping("/{id}/vacated")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public WorkerEntityDTO changeIsVacated(@PathVariable Long id, @RequestParam Boolean isVacated) {
        return workerEntityService.changeIsVacated(id, isVacated);
    }

    @PatchMapping("/{id}/vacated-in-3-months")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public WorkerEntityDTO changeIsVacatedIn3Months(@PathVariable Long id, @RequestParam Boolean isVacated) {
        return workerEntityService.changeIsVacatedIn3Months(id, isVacated);
    }

    @PatchMapping("/{id}/category-next-year")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public WorkerEntityDTO changeIsCategoryNextYear(@PathVariable Long id, @RequestParam Boolean isCategoryNextYear) {
        return workerEntityService.changeIsCategoryNextYear(id, isCategoryNextYear);
    }

    @PatchMapping("/{id}/ecc-next-month")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public WorkerEntityDTO changeIsEccNextMonth(@PathVariable Long id, @RequestParam Boolean isEccNextMonth) {
        return workerEntityService.changeIsEccNextMonth(id, isEccNextMonth);
    }

    @PatchMapping("/{id}/category-next-3-months")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public WorkerEntityDTO changeIsCategoryInNext3Months(@PathVariable Long id, @RequestParam Boolean isCategoryInNext3Months) {
        return workerEntityService.changeIsCategoryInNext3Months(id, isCategoryInNext3Months);
    }

    @PatchMapping("/{id}/qualification-next-month")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public WorkerEntityDTO changeIsQualificationNextMonth(@PathVariable Long id, @RequestParam Boolean isQualificationNextMonth) {
        return workerEntityService.changeIsQualificationNextMonth(id, isQualificationNextMonth);
    }

    @PatchMapping("/{id}/bday5")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public WorkerEntityDTO changeIsBday5(@PathVariable Long id, @RequestParam Boolean isBday5) {
        return workerEntityService.changeIsBday5(id, isBday5);
    }
}
