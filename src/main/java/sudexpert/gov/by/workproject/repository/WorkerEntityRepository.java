package sudexpert.gov.by.workproject.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sudexpert.gov.by.workproject.model.WorkerEntity;

import java.util.Optional;

@Repository
public interface WorkerEntityRepository extends JpaRepository<WorkerEntity, Long> {

    @EntityGraph(attributePaths = {"categories", "trains", "eccs", "qualifications", "vacations"})
    Optional<WorkerEntity> findById(Long id);
}
