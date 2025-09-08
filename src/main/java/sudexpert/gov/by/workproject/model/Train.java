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
@Table(name = "train")
@Schema(description = "Train entity info")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id", example = "3")
    private Long id;

    @Column(name = "worker_id")
    @Schema(description = "Id работника", example = "2")
    private Long workerId;

    @Column(name = "train_title")
    @Schema(description = "Название стажировки", example = "Плановая стажировка")
    private String title;

    @Column(name = "train_start_date")
    @Schema(description = "Дата начала стажировки", example = "2026-01-12")
    private LocalDate start;

    @Column(name = "train_end_date")
    @Schema(description = "Дата конца стажировки", example = "2026-02-08")
    private LocalDate end;

    @Column(name = "description")
    @Schema(description = "Описание стажировки (если нужно)", example = "...")
    private String description;

}
