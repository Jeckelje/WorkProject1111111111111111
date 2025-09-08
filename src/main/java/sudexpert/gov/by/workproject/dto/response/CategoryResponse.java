package sudexpert.gov.by.workproject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Category Response DTO")
public record CategoryResponse(

        @Schema(description = "Id", example = "3")
        Long id,

        @Schema(description = "Id работника", example = "2")
        Long workerId,

        @Schema(description = "Название категории", example = "Получение квалификации эксперта")
        String title,

        @Schema(description = "Дата получения категории", example = "2024-09-18")
        LocalDate start,

        @Schema(description = "Дата окончания категории", example = "2026-11-21")
        LocalDate end,

        @Schema(description = "Описание категории (если нужно)", example = "...")
        String description

) {
}
