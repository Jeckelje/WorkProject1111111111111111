package sudexpert.gov.by.workproject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Qualification Response DTO")
public record QualificationResponse(

        @Schema(description = "Id", example = "3")
        Long id,

        @Schema(description = "Id работника", example = "2")
        Long workerId,

        @Schema(description = "Название квалификации", example = "Получение квалификации эксперта")
        String title,

        @Schema(description = "Дата получения квалификации", example = "2023-10-23")
        LocalDate start,

        @Schema(description = "Дата окончания квалификации", example = "2026-09-30")
        LocalDate end,

        @Schema(description = "Описание квалификации (если нужно)", example = "...")
        String description

) {
}
