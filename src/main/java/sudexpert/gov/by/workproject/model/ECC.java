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
@Table(name = "ECC")
@Schema(description = "ECC entity info")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ECC {

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

    @Column(name = "ecc_title")
    @Schema(description = "Название ЭКК", example = "Получение квалификации эксперта")
    String title;

    @Column(name = "ecc_start_date")
    @Schema(description = "Дата начала ЭКК", example = "2026-09-23")
    LocalDate start;

    @Column(name = "ecc_end_date")
    @Schema(description = "Дата окончания ЭКК", example = "2026-09-30")
    LocalDate end;

    @Column(name = "description")
    @Schema(description = "Описание ЭКК (если нужно)", example = "...")
    String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ECC that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }
}
