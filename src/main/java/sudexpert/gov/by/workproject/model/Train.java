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
@Table(name = "train")
@Schema(description = "Train entity info")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Train {

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

    @Column(name = "train_title")
    @Schema(description = "Название стажировки", example = "Плановая стажировка")
    String title;

    @Column(name = "train_start_date")
    @Schema(description = "Дата начала стажировки", example = "2026-01-12")
    LocalDate start;

    @Column(name = "train_end_date")
    @Schema(description = "Дата конца стажировки", example = "2026-02-08")
    LocalDate end;

    @Column(name = "description")
    @Schema(description = "Описание стажировки (если нужно)", example = "...")
    String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Train that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }

}
