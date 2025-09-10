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
import sudexpert.gov.by.workproject.dto.error.AppError;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;

import java.util.List;

@Tag(name = "Контроллер Стажировок", description = "Train API")
public interface TrainAPI {

    @Operation(summary = "Create train")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Train created successfully", content = @Content(schema = @Schema(implementation = TrainDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    TrainDTO createTrain(@RequestBody @Validated(OnCreate.class) TrainDTO trainDTO);


    @Operation(summary = "Update train")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Train updated successfully", content = @Content(schema = @Schema(implementation = TrainDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "404", description = "Train not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    TrainDTO updateTrain(@PathVariable Long id, @RequestBody @Valid TrainDTO trainDTO);


    @Operation(summary = "Delete train")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Train deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Train not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    void deleteTrain(@PathVariable Long id);


    @Operation(summary = "Get train by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Train retrieved successfully", content = @Content(schema = @Schema(implementation = TrainDTO.class))),
            @ApiResponse(responseCode = "404", description = "Train not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    TrainDTO getTrainById(@PathVariable Long id);


    @Operation(summary = "Get trains by worker id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trains retrieved successfully", content = @Content(schema = @Schema(implementation = TrainDTO.class))),
            @ApiResponse(responseCode = "404", description = "Train not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    List<TrainDTO> getTrainsByWorkerId(@PathVariable Long workerId);


    @Operation(summary = "Get all trains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trains retrieved successfully", content = @Content(schema = @Schema(implementation = TrainDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    List<TrainDTO> getAllTrains();

}
