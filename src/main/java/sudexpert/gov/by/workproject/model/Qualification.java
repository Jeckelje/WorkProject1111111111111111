package sudexpert.gov.by.workproject.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "qualification")
@Schema(description = "Qualification entity info")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id", example = "3")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    WorkerEntity worker;

    @Column(name = "worker_id",insertable = false, updatable = false)
    @Schema(description = "Id работника", example = "2")
    Long workerId;

    @Column(name = "qualification_title")
    @Schema(description = "Название квалификации", example = "Получение квалификации эксперта")
    String title;

    @Column(name = "qualification_start_date")
    @Schema(description = "Дата получения квалификации", example = "2023-10-23")
    LocalDate start;

    @Column(name = "qualification_end_date")
    @Schema(description = "Дата окончания квалификации", example = "2026-09-30")
    LocalDate end;

    @Column(name = "description")
    @Schema(description = "Описание квалификации (если нужно)", example = "...")
    String description;

}
