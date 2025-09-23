package sudexpert.gov.by.workproject.scheduling;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sudexpert.gov.by.workproject.model.WorkerEntity;
import sudexpert.gov.by.workproject.repository.VacationRepository;
import sudexpert.gov.by.workproject.repository.WorkerEntityRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class FlagUpdater {

    WorkerEntityRepository workerEntityRepository;
    VacationRepository vacationRepository;

    @Scheduled(fixedRate = 300000000) // каждые 30 секунд
    @Transactional
    public void updateAllFlags() {
        LocalDate today = LocalDate.now();

        List<WorkerEntity> workers = workerEntityRepository.findAll();

        for (WorkerEntity worker : workers) {
            boolean wasVacated = worker.getIsVacated();

            // === 1. Флаг "Сотрудник в отпуске сейчас" ===
            boolean isVacatedNow = worker.getVacations().stream()
                    .anyMatch(v -> !today.isBefore(v.getStart()) && !today.isAfter(v.getEnd()));
            worker.setIsVacated(isVacatedNow);

            // Удаляем устаревшие отпуска
            worker.getVacations().removeIf(v -> today.isAfter(v.getEnd()));
            vacationRepository.deleteAll(
                    vacationRepository.findAll().stream()
                            .filter(v -> today.isAfter(v.getEnd()) && v.getWorker().equals(worker))
                            .toList()
            );

            // === 2. Флаг "Будет ли в отпуске в ближайшие 90 дней" ===
            worker.setIsVacatedIn3Months(worker.getVacations().stream()
                    .anyMatch(v -> !v.getStart().isBefore(today) && !v.getStart().isAfter(today.plusDays(90)))
            );

            // === 3. Флаг "Будет ли категория в ближайшие 365 дней" ===
            worker.setIsCategoryNextYear(worker.getCategories().stream()
                    .anyMatch(c -> !c.getStart().isBefore(today) && !c.getStart().isAfter(today.plusDays(365)))
            );

            // === 4. Флаг "Стажировка в ближайшие 30 дней" ===
            worker.setIsTrainNextMonth(worker.getTrains().stream()
                    .filter(t -> !t.getStart().isBefore(today)) // только будущие
                    .anyMatch(t -> !t.getStart().isAfter(today.plusDays(30))) // в ближайшие 30 дней
            );

            // === 5. Флаг "ЭКК в ближайшие 30 дней" ===
            worker.setIsEccNextMonth(worker.getEccs().stream()
                    .filter(e -> !e.getStart().isBefore(today))
                    .anyMatch(e -> !e.getStart().isAfter(today.plusDays(30)))
            );

            // === 6. Флаг "Повышение квалификации в ближайшие 30 дней" ===
            worker.setIsQualificationNextMonth(worker.getQualifications().stream()
                    .filter(q -> !q.getStart().isBefore(today))
                    .anyMatch(q -> !q.getStart().isAfter(today.plusDays(30)))
            );

            // === 7. Флаг "Будет ли категория в ближайшие 3 месяца" ===
            worker.setIsCategoryNext3Month(worker.getCategories().stream()
                    .anyMatch(c -> !c.getStart().isBefore(today) && c.getStart().isBefore(today.plusMonths(3)))
            );

            // === 8. Флаг "Кратен ли день рождения 5" ===
            if (worker.getBirthDay() != null) {
                int age = today.getYear() - worker.getBirthDay().getYear();
                worker.setIsBday5(age % 5 == 0);
            } else {
                worker.setIsBday5(false);
            }

            if (wasVacated != isVacatedNow) {
                log.info("Флаг isVacated у сотрудника {} изменён: {} → {}", worker.getId(), wasVacated, isVacatedNow);
            }
        }
    }
}
