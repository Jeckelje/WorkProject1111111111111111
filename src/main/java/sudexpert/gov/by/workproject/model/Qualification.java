package sudexpert.gov.by.workproject.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "qualification")
@Schema(description = "Qualification entity info")
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id", example = "3")
    private Long id;

    @Column(name = "worker_id")
    @Schema(description = "Id работника", example = "2")
    private Long workerId;

    @Column(name = "qualification_title")
    @Schema(description = "Название квалификации", example = "Получение квалификации эксперта")
    private String title;

    @Column(name = "qualification_start_date")
    @Schema(description = "Дата получения квалификации", example = "2023-10-23")
    private LocalDate start;

    @Column(name = "qualification_end_date")
    @Schema(description = "Дата окончания квалификации", example = "2026-09-30")
    private LocalDate end;

    @Column(name = "description")
    @Schema(description = "Описание квалификации (если нужно)", example = "...")
    private String description;

}
