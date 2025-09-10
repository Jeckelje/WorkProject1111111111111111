package sudexpert.gov.by.workproject.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
@Schema(description = "Category entity info")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id", example = "3")
    Long id;

    @Column(name = "worker_id",insertable = false, updatable = false)
    @Schema(description = "Id работника", example = "2")
    Long workerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    WorkerEntity worker;

    @Column(name = "category_title")
    @Schema(description = "Название категории", example = "Получение квалификации эксперта")
    String title;

    @Column(name = "category_start_date")
    @Schema(description = "Дата получения категории", example = "2024-09-18")
    LocalDate start;

    @Column(name = "category_end_date")
    @Schema(description = "Дата окончания категории", example = "2026-11-21")
    LocalDate end;

    @Column(name = "description")
    @Schema(description = "Описание категории (если нужно)", example = "...")
    String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }

}
