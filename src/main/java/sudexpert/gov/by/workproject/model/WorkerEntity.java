package sudexpert.gov.by.workproject.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "workers")
@Schema(description = "Worker Entity Info.")
public class WorkerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "id", example = "2")
    private Long id;

    @Schema(description = "Имя", example = "Иван")
    @Column(name = "name")
    private String name;

    @Schema(description = "Фамилия", example = "Иванов")
    @Column(name = "surname")
    private String surname;

    @Schema(description = "Отчество", example = "Иванович")
    @Column(name = "patronomicName")
    private String patronymicName;

    @Schema(description = "Должность", example = "Эксперт")
    @Column(name = "job_title")
    private String jobTitle;

    //@ElementCollection(targetClass = Train.class)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    @Schema(description = "")
    private Set<Train> trains;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    @Schema(description = "")
    private Set<Category> categories;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    @Schema(description = "")
    private Set<ECC> eccs;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    @Schema(description = "")
    private Set<Qualification> qualifications;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    @Schema(description = "")
    private Set<Vacation> vacations;

    @Schema(description = "Находится ли в отпуске", example = "true")
    @Column(name = "isVacated")
    private boolean isVacated;

    @Schema(description = "Будет ли в отпуске в ближайшие 3 месяца", example = "true")
    @Column(name = "isVacatedIn3Months")
    private boolean isVacatedIn3Months;

    @Schema(description = "Будет ли категория в следующем году", example = "true")
    @Column(name = "isCategoryNextYear")
    private boolean isCategoryNextYear;

    @Schema(description = "Стажировка в следующем месяце", example = "true")
    @Column(name = "isTrainNextMonth")
    private boolean isTrainNextMonth;

    @Schema(description = "Будет ли ЭКК в следующием месяце", example = "true")
    @Column(name = "isEccNextMonth")
    private boolean isEccNextMonth;

    @Schema(description = "Будет ли повышение квалификации в следующем месяце", example = "true")
    @Column(name = "isQualificationNextMonth")
    private boolean isQualificationNextMonth;

    @Schema(description = "Будет ли категория в следующих 3 месяцев", example = "true")
    @Column(name = "isCategoryNext3Month")
    private boolean isCategoryNext3Month;
}
