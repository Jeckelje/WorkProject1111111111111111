package sudexpert.gov.by.workproject.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.dto.error.AppError;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;

import java.util.List;

@Tag(name = "Контроллер Сотрудников", description = "WorkerEntity API")
public interface WorkerEntityAPI {

    @Operation(summary = "Create worker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Worker created successfully", content = @Content(schema = @Schema(implementation = WorkerEntityDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    WorkerEntityDTO createWorker(@RequestBody @Validated(OnCreate.class) WorkerEntityDTO workerEntityDTO);

    @Operation(summary = "Update worker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Worker updated successfully", content = @Content(schema = @Schema(implementation = WorkerEntityDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "404", description = "Worker not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    WorkerEntityDTO updateWorker(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) WorkerEntityDTO workerEntityDTO);

    @Operation(summary = "Delete worker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Worker deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Worker not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    void deleteWorker(@PathVariable Long id);

    @Operation(summary = "Get worker by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Worker retrieved successfully", content = @Content(schema = @Schema(implementation = WorkerEntityDTO.class))),
            @ApiResponse(responseCode = "404", description = "Worker not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    WorkerEntityDTO getWorkerById(@PathVariable Long id);

    @Operation(summary = "Get all workers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workers retrieved successfully", content = @Content(schema = @Schema(implementation = WorkerEntityDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    List<WorkerEntityDTO> getAllWorkers();

    // Методы для изменения флагов
    @Operation(summary = "Change isVacated flag")
    WorkerEntityDTO changeIsVacated(@PathVariable Long id, @RequestParam Boolean isVacated);

    @Operation(summary = "Change isVacatedIn3Months flag")
    WorkerEntityDTO changeIsVacatedIn3Months(@PathVariable Long id, @RequestParam Boolean isVacated);

    @Operation(summary = "Change isCategoryNextYear flag")
    WorkerEntityDTO changeIsCategoryNextYear(@PathVariable Long id, @RequestParam Boolean isCategoryNextYear);

    @Operation(summary = "Change isEccNextMonth flag")
    WorkerEntityDTO changeIsEccNextMonth(@PathVariable Long id, @RequestParam Boolean isEccNextMonth);

    @Operation(summary = "Change isCategoryInNext3Months flag")
    WorkerEntityDTO changeIsCategoryInNext3Months(@PathVariable Long id, @RequestParam Boolean isCategoryInNext3Months);

    @Operation(summary = "Change isQualificationNextMonth flag")
    WorkerEntityDTO changeIsQualificationNextMonth(@PathVariable Long id, @RequestParam Boolean isQualificationNextMonth);

    @Operation(summary = "Change isBday5 flag")
    WorkerEntityDTO changeIsBday5(@PathVariable Long id, @RequestParam Boolean isBday5);
}
