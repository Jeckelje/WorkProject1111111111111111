package sudexpert.gov.by.workproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sudexpert.gov.by.workproject.model.WorkerEntity;

@Repository
public interface WorkerEntityRepository extends JpaRepository<WorkerEntity, Long> {
}
