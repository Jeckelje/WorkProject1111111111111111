package sudexpert.gov.by.workproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sudexpert.gov.by.workproject.model.Vacation;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {

    List<Vacation> findVacationsByWorkerId(Long workerId);

}
