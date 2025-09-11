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
            @Schema(description ="Работник", example = """
                      "id": 1,
                      "name": "Иван",
                      "surname": "Иванов",
                      "patronymicName": "Иванович",
                      "jobTitle": "Ведущий эксперт",
                      "trains": [
                        {
                          "id": 9,
                          "workerId": 1,
                          "title": "Специализированная стажировка",
                          "start": "2024-03-10",
                          "end": "2024-03-20",
                          "description": "Стажировка по новому оборудованию"
                        },
                        {
                          "id": 8,
                          "workerId": 1,
                          "title": "Плановая стажировка",
                          "start": "2024-01-15",
                          "end": "2024-01-30",
                          "description": "Ежегодная плановая стажировка по технике безопасности"
                        }
                      ],
                      "categories": [
                        {
                          "id": 8,
                          "workerId": 1,
                          "title": "Высшая категория эксперта",
                          "start": "2024-09-18",
                          "end": "2026-11-21",
                          "description": "Присвоение высшей квалификационной категории"
                        },
                        {
                          "id": 9,
                          "workerId": 1,
                          "title": "Первая категория специалиста",
                          "start": "2023-05-15",
                          "end": "2025-05-14",
                          "description": "Первая квалификационная категория"
                        }
                      ],
                      "eccs": [
                        {
                          "id": 9,
                          "workerId": 1,
                          "title": "Внеплановая проверка по новым нормативам",
                          "start": "2024-11-15",
                          "end": "2024-11-20",
                          "description": "Проверка знаний новых нормативных документов"
                        },
                        {
                          "id": 8,
                          "workerId": 1,
                          "title": "Плановая внеочередная проверка знаний",
                          "start": "2024-09-23",
                          "end": "2024-09-30",
                          "description": "Ежегодная плановая проверка знаний"
                        }
                      ],
                      "qualifications": [
                        {
                          "id": 9,
                          "workerId": 1,
                          "title": "Повышение категории",
                          "start": "2024-03-15",
                          "end": "2027-03-14",
                          "description": "Переход на высшую категорию"
                        },
                        {
                          "id": 8,
                          "workerId": 1,
                          "title": "Получение квалификации эксперта",
                          "start": "2023-10-23",
                          "end": "2026-09-30",
                          "description": "Основная профессиональная квалификация"
                        }
                      ],
                      "vacations": [
                        {
                          "id": 8,
                          "workerId": 1,
                          "title": "Плановый ежегодный оплачиваемый отпуск",
                          "start": "2024-07-01",
                          "end": "2024-07-28",
                          "description": "Ежегодный отпуск по графику"
                        },
                        {
                          "id": 9,
                          "workerId": 1,
                          "title": "Дополнительный отпуск",
                          "start": "2024-12-15",
                          "end": "2024-12-22",
                          "description": "Дополнительные дни за ненормированный рабочий день"
                        }
                      ],
                      "isVacated": false,
                      "isVacatedIn3Months": true,
                      "isCategoryNextYear": false,
                      "isTrainNextMonth": true,
                      "isEccNextMonth": false,
                      "isQualificationNextMonth": true,
                      "isCategoryNext3Month": false,
                      "isBday5": false,
                      "bDay": null\
                    """)
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
