package sudexpert.gov.by.workproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;
import sudexpert.gov.by.workproject.model.ECC;

import java.time.LocalDate;

@Schema(implementation = ECC.class)
public record ECCDTO(

        @NotNull(message = "", groups = OnUpdate.class)
        Long id,

        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        Long workerId,

        @NotEmpty(message = "", groups = {OnCreate.class, OnUpdate.class})
        String title,

        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate start,

        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate end,

        @NotEmpty(message = "", groups = {OnCreate.class, OnUpdate.class})
        String description
) {
    public ECCDTO {
        if (start != null && end != null && end.isBefore(start)) {
            throw new IllegalArgumentException("Planned completion date cannot be earlier than the start date.");
        }
    }
}

