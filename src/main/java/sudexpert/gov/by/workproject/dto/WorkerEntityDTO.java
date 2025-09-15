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

        Long id,

        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        String name,

        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        String surname,

        String patronymicName,

        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        String jobTitle,


        @NotNull(message = "", groups = {OnUpdate.class, OnCreate.class})
        LocalDate birthDay,

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

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Boolean isVacated,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Boolean isVacatedIn3Months,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Boolean isCategoryNextYear,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Boolean isTrainNextMonth,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Boolean isEccNextMonth,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Boolean isQualificationNextMonth,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Boolean isCategoryNext3Month,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Boolean isBday5

) {


}

