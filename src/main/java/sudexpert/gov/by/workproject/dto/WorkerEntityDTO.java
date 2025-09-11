package sudexpert.gov.by.workproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;
import sudexpert.gov.by.workproject.model.WorkerEntity;

import java.time.LocalDate;
import java.util.Set;


@Schema(implementation = WorkerEntity.class)
public record WorkerEntityDTO(

        @NotNull(message = "", groups = OnUpdate.class)
        Long id,

        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        String name,

        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        String surname,

        String patronymicName,

        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        String jobTitle,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Set<TrainDTO> trains,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Set<CategoryDTO> categories,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Set<ECCDTO> eccs,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Set<QualificationDTO> qualifications,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Set<VacationDTO> vacations,

        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isVacated,

        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isVacatedIn3Months,

        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isCategoryNextYear,

        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isTrainNextMonth,

        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isEccNextMonth,

        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isQualificationNextMonth,

        @NotNull(message = "", groups = OnUpdate.class)
        Boolean isCategoryNext3Month,

        @NotNull(groups = {OnUpdate.class})
        Boolean isBday5,

        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        LocalDate birthDay
) {


}

