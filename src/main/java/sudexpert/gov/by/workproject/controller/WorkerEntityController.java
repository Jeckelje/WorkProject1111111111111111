package sudexpert.gov.by.workproject.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
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

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("api/workers")
@RequiredArgsConstructor
@Validated
@Tag(name = "Контроллер работников", description = "WorkerEntity API")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkerEntityController {

    WorkerEntityService workerEntityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @Operation(summary = "Create WorkerEntity")
    public WorkerEntityDTO create(@Validated(OnCreate.class) @RequestBody WorkerEntityDTO workerEntityDTO) {
        return workerEntityService.createWorkerEntity(workerEntityDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update workerEntity")
    @Transactional
    public WorkerEntityDTO updateWorkerEntity(@Validated(OnUpdate.class) @RequestBody WorkerEntityDTO workerEntityDTO,
                                              @PathVariable Long id) {

        return workerEntityService.updateWorkerEntity(id, workerEntityDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete workerEntity")
    @Transactional
    public void deleteWorkerEntity(@PathVariable Long id) {

        workerEntityService.deleteWorkerEntity(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get workerEntity by id")
    @Transactional
    public WorkerEntityDTO getWorkerEntityById(@PathVariable Long id) {
        return workerEntityService.getWorkerEntityById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all workers")
    @Transactional
    public List<WorkerEntityDTO> getAllWorkers() {
        return workerEntityService.getAllWorkers();
    }

    @PutMapping("/changeIsCategoryInNext3Months/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change is category in next 3 months")
    @Transactional
    public WorkerEntityDTO changeIsCategoryInNext3Months(@PathVariable Long id, @RequestBody @NotNull Boolean isCategoryInNext3Months) {
        return workerEntityService.changeIsCategoryInNext3Months(id, isCategoryInNext3Months);
    }

    @PutMapping("/changeIsCategoryNextYear/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change is category nex year")
    @Transactional
    public WorkerEntityDTO changeIsCategoryNextYear(@PathVariable Long id, @RequestBody @NotNull Boolean isCategoryInNextYear) {
        return workerEntityService.changeIsCategoryInNext3Months(id, isCategoryInNextYear);
    }

    @PutMapping("/changeIsEccNextMonth/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change is ecc next month")
    @Transactional
    public WorkerEntityDTO changeIsEccNextMonth(@PathVariable Long id, @RequestBody @NotNull Boolean isEccNextMonth) {
        return workerEntityService.changeIsEccNextMonth(id, isEccNextMonth);
    }

    @PutMapping("/changeIsQualificationNextMonth/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change is qualification next month")
    @Transactional
    public WorkerEntityDTO changeIsQualificationNextMonth(@PathVariable Long id, @RequestBody @NotNull Boolean isQualificationNextMonth) {
        return workerEntityService.changeIsQualificationNextMonth(id, isQualificationNextMonth);
    }

    @PutMapping("/changeIsVacated/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change is vacated")
    @Transactional
    public WorkerEntityDTO changeIsVacated(@PathVariable Long id, @RequestBody @NotNull Boolean isVacated) {
        return workerEntityService.changeIsVacated(id, isVacated);
    }

    @PutMapping("/changeIsVacatedIn3Months/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change is vacated in 3 months")
    @Transactional
    public WorkerEntityDTO changeIsVacatedIn3Months(@PathVariable Long id, @RequestBody @NotNull Boolean isVacatedIn3Months) {
        return workerEntityService.changeIsVacatedIn3Months(id, isVacatedIn3Months);
    }

    @PutMapping("/changeIsBday5/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change is vacated in 3 months")
    @Transactional
    public WorkerEntityDTO changeIsBday5(@PathVariable Long id, @RequestBody @NotNull Boolean isBday5) {
        return workerEntityService.changeIsBday5(id, isBday5);
    }

}
