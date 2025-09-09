package sudexpert.gov.by.workproject.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import sudexpert.gov.by.workproject.dto.TrainDTO;
import sudexpert.gov.by.workproject.dto.VacationDTO;
import sudexpert.gov.by.workproject.dto.error.AppError;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;

import java.util.List;

@Tag(name = "Контроллер Отпусков", description = "Vacation API")
public interface VacationAPI {

    @Operation(summary = "Create vacation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vacation created successfully", content = @Content(schema = @Schema(implementation = VacationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    VacationDTO createVacation(@RequestBody @Validated(OnCreate.class) VacationDTO vacationDTO);


    @Operation(summary = "Update vacation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacation updated successfully", content = @Content(schema = @Schema(implementation = VacationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "404", description = "Vacation not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    VacationDTO updateVacation(@PathVariable Long id, @RequestBody @Valid VacationDTO vacationDTO);


    @Operation(summary = "Delete vacation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vacation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Vacation not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    void deleteVacation(@PathVariable Long id);


    @Operation(summary = "Get vacation by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacation retrieved successfully", content = @Content(schema = @Schema(implementation = VacationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vacation not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    VacationDTO getVacationById(@PathVariable Long id);


    @Operation(summary = "Get vacations by worker id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacations retrieved successfully", content = @Content(schema = @Schema(implementation = VacationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vacation not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    List<VacationDTO> getVacationsByWorkerId(@PathVariable Long workerId);


    @Operation(summary = "Get all vacations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacations retrieved successfully", content = @Content(schema = @Schema(implementation = VacationDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    List<VacationDTO> getAllVacations();

}
