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
@Table(name = "category")
@Schema(description = "Category entity info")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id", example = "3")
    private Long id;

    @Column(name = "worker_id")
    @Schema(description = "Id работника", example = "2")
    private Long workerId;

    @Column(name = "category_title")
    @Schema(description = "Название категории", example = "Получение квалификации эксперта")
    private String title;

    @Column(name = "category_start_date")
    @Schema(description = "Дата получения категории", example = "2024-09-18")
    private LocalDate start;

    @Column(name = "category_end_date")
    @Schema(description = "Дата окончания категории", example = "2026-11-21")
    private LocalDate end;

    @Column(name = "description")
    @Schema(description = "Описание категории (если нужно)", example = "...")
    private String description;

}
