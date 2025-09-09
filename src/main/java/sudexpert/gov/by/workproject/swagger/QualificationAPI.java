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
import sudexpert.gov.by.workproject.dto.QualificationDTO;
import sudexpert.gov.by.workproject.dto.TrainDTO;
import sudexpert.gov.by.workproject.dto.error.AppError;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;

import java.util.List;

@Tag(name = "Контроллер Квалификаций", description = "Qualification API")
public interface QualificationAPI {

    @Operation(summary = "Create qualification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Qualification created successfully", content = @Content(schema = @Schema(implementation = QualificationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    QualificationDTO createQualification(@RequestBody @Validated(OnCreate.class) QualificationDTO qualificationDTO);


    @Operation(summary = "Update qualification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Qualification updated successfully", content = @Content(schema = @Schema(implementation = QualificationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "404", description = "Qualification not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    QualificationDTO updateQualification(@PathVariable Long id, @RequestBody @Valid QualificationDTO qualificationDTO);

    @Operation(summary = "Delete qualification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Qualification deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Qualification not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    void deleteQualification(@PathVariable Long id);

    @Operation(summary = "Get qualification by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Qualification retrieved successfully", content = @Content(schema = @Schema(implementation = QualificationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Qualification not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    QualificationDTO getQualificationById(@PathVariable Long id);

    @Operation(summary = "Get qualifications by worker id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Qualifications retrieved successfully", content = @Content(schema = @Schema(implementation = QualificationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Qualifications not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    List<QualificationDTO> getQualificationsByWorkerId(@PathVariable Long workerId);

    @Operation(summary = "Get all qualifications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Qualifications retrieved successfully", content = @Content(schema = @Schema(implementation = QualificationDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    List<QualificationDTO> getAllQualification();

}
