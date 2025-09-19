package sudexpert.gov.by.workproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;
import sudexpert.gov.by.workproject.model.Category;

import java.time.LocalDate;
@Schema(implementation = Category.class)
     public record CategoryDTO (
    @NotNull(message = "",groups = OnUpdate.class)
    Long id,

    @NotNull(message = "",groups = {OnUpdate.class, OnCreate.class})
    Long workerId,

    @NotEmpty(message = "",groups = {OnCreate.class, OnUpdate.class})
    String title,

    @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
    LocalDate start,

    @NotNull(message = "",groups = {OnUpdate.class, OnCreate.class})
    LocalDate end,

    String description
)
     {
         public CategoryDTO{
             if (start != null && end != null && end.isBefore(start)) {
                 throw new IllegalArgumentException("Planned completion date cannot be earlier than the start date.");
             }
         }
     }

