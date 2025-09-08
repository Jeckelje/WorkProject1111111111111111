package sudexpert.gov.by.workproject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import sudexpert.gov.by.workproject.model.*;

import java.util.Set;

@Schema(description = "Worker Entity Response DTO")
public record WorkerEntityResponse(

        @Schema(description = "Unique identifier of the worker", example = "1")
        Long id,

        @Schema(description = "Worker name", example = "Иван")
        String name,

        @Schema(description = "Worker surname", example = "Иванов")
        String surname,

        @Schema(description = "Worker patronymicName", example = "Иванович")
        String patronymicName,

        @Schema(description = "Должность", example = "Эксперт")
        String jobTitle,

        @Schema(description = "")
        Set<Train> trains,

        @Schema(description = "")
        Set<Category> categories,

        @Schema(description = "")
        Set<ECC> eccs,

        @Schema(description = "")
        Set<Qualification> qualifications,

        @Schema(description = "")
        Set<Vacation> vacations,

        @Schema(description = "Находится ли в отпуске", example = "true")
        boolean isVacated,

        @Schema(description = "Будет ли в отпуске в ближайшие 3 месяца", example = "true")
        boolean isVacatedIn3Months,

        @Schema(description = "Будет ли категория в следующем году", example = "true")
        boolean isCategoryNextYear,

        @Schema(description = "Стажировка в следующем месяце", example = "true")
        boolean isTrainNextMonth,

        @Schema(description = "Будет ли ЭКК в следующием месяце", example = "true")
        boolean isEccNextMonth,

        @Schema(description = "Будет ли повышение квалификации в следующем месяце", example = "true")
        boolean isQualificationNextMonth,

        @Schema(description = "Будет ли категория в следующих 3 месяцев", example = "true")
        boolean isCategoryNext3Month

) {
}
