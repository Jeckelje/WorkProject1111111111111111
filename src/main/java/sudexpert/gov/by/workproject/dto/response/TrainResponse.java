package sudexpert.gov.by.workproject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Train Response DTO")
public record TrainResponse(

        @Schema(description = "Id", example = "3")
        Long id,

        @Schema(description = "Id работника", example = "2")
        Long workerId,

        @Schema(description = "Название стажировки", example = "Плановая стажировка")
        String title,

        @Schema(description = "Дата начала стажировки", example = "2026-01-12")
        LocalDate start,

        @Schema(description = "Дата конца стажировки", example = "2026-02-08")
        LocalDate end,

        @Schema(description = "Описание стажировки (если нужно)", example = "...")
        String description

) {
}
