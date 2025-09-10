package sudexpert.gov.by.workproject.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vacation")
@Schema(description = "Vacation entity info")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id", example = "3")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    WorkerEntity worker;

    @Column(name = "worker_id",insertable = false, updatable = false)
    @Schema(description = "ID работника", example = "2")
    Long workerId;

    @Column(name = "vacation_title")
    @Schema(description = "Название отпуска", example = "Плановый ежегодный оплачиваемый отпуск")
    String title;

    @Column(name = "vacation_start_date")
    @Schema(description = "Дата начала отпуска", example = "2025-10-28")
    LocalDate start;

    @Column(name = "vacation_end_date")
    @Schema(description = "Дата конца отпуска", example = "2025-11-25")
    LocalDate end;

    @Column(name = "description")
    @Schema(description = "Описание отпуска (если нужно)", example = "...")
    String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vacation that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }

}
