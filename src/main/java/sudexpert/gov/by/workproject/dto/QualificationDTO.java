package sudexpert.gov.by.workproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;

import java.time.LocalDate;

public record QualificationDTO(
@Schema(description = "Id", example = "3")
@NotNull(message = "",groups = OnUpdate.class)
Long id,

@Schema(description = "Id работника", example = "2")
@NotNull(message = "",groups = {OnUpdate.class, OnCreate.class})
Long workerId,

@Schema(description = "Название категории", example = "Получение квалификации эксперта")
@NotEmpty(message = "",groups = {OnCreate.class, OnUpdate.class})
String title,

@Schema(description = "Дата получения категории", example = "2024-09-18")
@NotNull(message = "",groups = {OnUpdate.class, OnCreate.class})
LocalDate start,

@Schema(description = "Дата окончания категории", example = "2026-11-21")
@NotNull(message = "",groups = {OnUpdate.class, OnCreate.class})
LocalDate end,

@Schema(description = "Описание категории (если нужно)", example = "...")
@NotEmpty(message = "",groups = {OnCreate.class, OnUpdate.class})
String description
)
{
    public QualificationDTO {
        if (start != null && end != null && end.isBefore(start)) {
            throw new IllegalArgumentException("Planned completion date cannot be earlier than the start date.");
        }
    }
}

