package sudexpert.gov.by.workproject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Vacation Response DTO")
public record VacationResponse(
        @Schema(description = "Id", example = "3")
        Long id,

        @Schema(description = "ID работника", example = "2")
        Long workerId,

        @Schema(description = "Название отпуска", example = "Плановый ежегодный оплачиваемый отпуск")
        String title,

        @Schema(description = "Дата начала отпуска", example = "2025-10-28")
        LocalDate start,

        @Schema(description = "Дата конца отпуска", example = "2025-11-25")
        LocalDate end,

        @Schema(description = "Описание отпуска (если нужно)", example = "...")
        String description
) {
}
