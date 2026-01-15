package sudexpert.gov.by.workproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;
import sudexpert.gov.by.workproject.model.Achievement;

import java.time.LocalDate;

@Schema(implementation = Achievement.class)
public record AchievementDTO(
        @NotNull(message = "", groups = OnUpdate.class)
        Long id, // обязательно только для обновления

        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        Long workerId,

        @NotEmpty(message = "", groups = {OnCreate.class, OnUpdate.class})
        String description,

        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date
) {}
