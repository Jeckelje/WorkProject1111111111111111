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
import sudexpert.gov.by.workproject.dto.ECCDTO;
import sudexpert.gov.by.workproject.dto.error.AppError;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;

import java.util.List;

@Tag(name = "Контроллер ЭКК", description = "ECC API")
public interface ECCAPI {

    @Operation(summary = "Create ECC")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ECC created successfully", content = @Content(schema = @Schema(implementation = ECCDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    ECCDTO createEcc(@RequestBody @Validated(OnCreate.class) ECCDTO eccdto);

    @Operation(summary = "Update ECC")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ECC updated successfully", content = @Content(schema = @Schema(implementation = ECCDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "404", description = "ECC not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    ECCDTO updateEcc(@PathVariable Long id, @RequestBody @Valid ECCDTO eccdto);

    @Operation(summary = "Delete ECC")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "ECC deleted successfully"),
            @ApiResponse(responseCode = "404", description = "ECC not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    void deleteEcc(@PathVariable Long id);

    @Operation(summary = "Get ECC by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ECC retrieved successfully", content = @Content(schema = @Schema(implementation = ECCDTO.class))),
            @ApiResponse(responseCode = "404", description = "ECC not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    ECCDTO getEccById(@PathVariable Long id);

    @Operation(summary = "Get ECCs by worker id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ECCs retrieved successfully", content = @Content(schema = @Schema(implementation = ECCDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    List<ECCDTO> getECCSByWorkerId(@PathVariable Long workerId);

    @Operation(summary = "Get all ECCs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ECCs retrieved successfully", content = @Content(schema = @Schema(implementation = ECCDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    List<ECCDTO> getAllECCS();

}
