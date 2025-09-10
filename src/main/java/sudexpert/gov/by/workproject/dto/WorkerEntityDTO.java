package sudexpert.gov.by.workproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;

import java.time.LocalDate;
import java.util.Set;

public record WorkerEntityDTO(
        @NotNull(message = "", groups = OnUpdate.class)
        @Schema(description = "id", example = "2")
        Long id,

        @Schema(description = "Имя", example = "Иван")
        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        String name,

        @Schema(description = "Фамилия", example = "Иванов")
        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        String surname,

        @Schema(description = "Отчество", example = "Иванович")
        String patronymicName,

        @Schema(description = "Должность", example = "Эксперт")
        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        String jobTitle,

        @Schema(description = "")
        Set<TrainDTO> trains,

        @Schema(description = "")
        Set<CategoryDTO> categories,

        @Schema(description = "")
        Set<ECCDTO> eccs,

        @Schema(description = "")
        Set<QualificationDTO> qualifications,

        @Schema(description = "")
        Set<VacationDTO> vacations,

        @Schema(description = "Находится ли в отпуске", example = "true")
        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isVacated,

        @Schema(description = "Будет ли в отпуске в ближайшие 3 месяца", example = "true")
        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isVacatedIn3Months,

        @Schema(description = "Будет ли категория в следующем году", example = "true")
        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isCategoryNextYear,

        @Schema(description = "Стажировка в следующем месяце", example = "true")
        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isTrainNextMonth,

        @Schema(description = "Будет ли ЭКК в следующием месяце", example = "true")
        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isEccNextMonth,

        @Schema(description = "Будет ли повышение квалификации в следующем месяце", example = "true")
        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isQualificationNextMonth,

        @Schema(description = "Будет ли категория в следующих 3 месяцев", example = "true")
        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isCategoryNext3Month,

        @Schema(description = "Кратен ли день рождения 5", example = "true")
@NotNull(groups = {OnUpdate.class, OnCreate.class})
                Boolean isBday5,

        @Schema(description = "Кратен ли день рождения 5", example = "true")
        @NotNull(message = "", groups = OnUpdate.class)
        LocalDate bDay
) {


}

