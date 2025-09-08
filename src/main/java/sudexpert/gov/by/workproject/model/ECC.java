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
@Table(name = "ECC")
@Schema(description = "ECC entity info")
public class ECC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id", example = "3")
    private Long id;

    @Column(name = "worker_id")
    @Schema(description = "Id работника", example = "2")
    private Long workerId;

    @Column(name = "ecc_title")
    @Schema(description = "Название ЭКК", example = "Получение квалификации эксперта")
    private String title;

    @Column(name = "ecc_start_date")
    @Schema(description = "Дата начала ЭКК", example = "2026-09-23")
    private LocalDate start;

    @Column(name = "ecc_end_date")
    @Schema(description = "Дата окончания ЭКК", example = "2026-09-30")
    private LocalDate end;

    @Column(name = "description")
    @Schema(description = "Описание ЭКК (если нужно)", example = "...")
    private String description;

}
