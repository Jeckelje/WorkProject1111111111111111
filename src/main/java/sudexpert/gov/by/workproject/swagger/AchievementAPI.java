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
import sudexpert.gov.by.workproject.dto.AchievementDTO;
import sudexpert.gov.by.workproject.dto.QualificationDTO;
import sudexpert.gov.by.workproject.dto.error.AppError;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;

import java.util.List;

@Tag(name = "Контроллер Регалий", description = "Achievement API")
public interface AchievementAPI {

    @Operation(summary = "Create achievement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Achievement created successfully", content = @Content(schema = @Schema(implementation = AchievementDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    AchievementDTO createAchievement(@RequestBody @Validated(OnCreate.class) AchievementDTO achievementDTO);


    @Operation(summary = "Update achievement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Achievement updated successfully", content = @Content(schema = @Schema(implementation = AchievementDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "404", description = "Achievement not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    AchievementDTO updateAchievement(@PathVariable Long id, @RequestBody @Valid AchievementDTO achievementDTO);

    @Operation(summary = "Delete achievement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Achievement deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Achievement not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    void deleteAchievement(@PathVariable Long id);

    @Operation(summary = "Get achievement by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Achievement retrieved successfully", content = @Content(schema = @Schema(implementation = AchievementDTO.class))),
            @ApiResponse(responseCode = "404", description = "Achievement not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    AchievementDTO getAchievementById(@PathVariable Long id);

    @Operation(summary = "Get achievements by worker id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Achievements retrieved successfully", content = @Content(schema = @Schema(implementation = AchievementDTO.class))),
            @ApiResponse(responseCode = "404", description = "Achievements not found", content = @Content(schema = @Schema(implementation = AppError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    List<AchievementDTO> getAchievementsByWorkerId(@PathVariable Long workerId);

    @Operation(summary = "Get all achievements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Achievements retrieved successfully", content = @Content(schema = @Schema(implementation = AchievementDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = AppError.class)))
    })
    List<AchievementDTO> getAllAchievements();

}
