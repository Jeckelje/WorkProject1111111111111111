package sudexpert.gov.by.workproject.dto.request.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Create Category Request DTO")
public record CreateECCDTO(

        @Schema(description = "Id работника", example = "2")
        Long workerId,

        @NotNull(message = "Название не может быть пустым")
        @Schema(description = "Название категории", example = "Получение квалификации эксперта")
        String title,

        @NotNull(message = "Дата не может быть пустой")
        @Schema(description = "Дата получения категории", example = "2024-09-18")
        LocalDate start,

        @NotNull(message = "Дата не может быть пустой")
        @Schema(description = "Дата окончания категории", example = "2026-11-21")
        LocalDate end,

        @Schema(description = "Описание категории (если нужно)", example = "...")
        String description

) {
        public CreateECCDTO {
                if (start != null && end != null && end.isBefore(start)) {
                        throw new IllegalArgumentException("Planned completion date cannot be earlier than the start date.");
                }
        }
}
