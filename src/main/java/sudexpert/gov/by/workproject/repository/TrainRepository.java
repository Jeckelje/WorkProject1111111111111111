package sudexpert.gov.by.workproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sudexpert.gov.by.workproject.model.Train;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {

    List<Train> findTrainsByWorkerId(Long workerId);

}
