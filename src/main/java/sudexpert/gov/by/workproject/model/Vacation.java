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
@Table(name = "vacation")
@Schema(description = "Vacation entity info")
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id", example = "3")
    private Long id;

    @Column(name = "worker_id")
    @Schema(description = "ID работника", example = "2")
    private Long workerId;

    @Column(name = "vacation_title")
    @Schema(description = "Название отпуска", example = "Плановый ежегодный оплачиваемый отпуск")
    private String title;

    @Column(name = "vacation_start_date")
    @Schema(description = "Дата начала отпуска", example = "2025-10-28")
    private LocalDate start;

    @Column(name = "vacation_end_date")
    @Schema(description = "Дата конца отпуска", example = "2025-11-25")
    private LocalDate end;

    @Column(name = "description")
    @Schema(description = "Описание отпуска (если нужно)", example = "...")
    private String description;

}
