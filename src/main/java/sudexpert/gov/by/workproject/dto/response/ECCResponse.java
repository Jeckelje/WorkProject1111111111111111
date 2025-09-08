package sudexpert.gov.by.workproject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "ECC Response DTO")
public record ECCResponse(

        @Schema(description = "Id", example = "3")
        Long id,

        @Schema(description = "Id работника", example = "2")
        Long workerId,

        @Schema(description = "Название ЭКК", example = "Получение квалификации эксперта")
        String title,

        @Schema(description = "Дата начала ЭКК", example = "2026-09-23")
        LocalDate start,

        @Schema(description = "Дата окончания ЭКК", example = "2026-09-30")
        LocalDate end,

        @Schema(description = "Описание ЭКК (если нужно)", example = "...")
        String description

) {
}
