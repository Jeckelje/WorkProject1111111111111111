package sudexpert.gov.by.workproject.service;

import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;

import java.util.List;

public interface WorkerEntityService {

    WorkerEntityDTO createWorkerEntity(WorkerEntityDTO workerEntityDTO);

    WorkerEntityDTO updateWorkerEntity(Long id, WorkerEntityDTO workerEntityDTO);

    WorkerEntityDTO getWorkerEntityById(Long id);

    List<WorkerEntityDTO> getAllWorkers();

    void deleteWorkerEntity(Long id);

    WorkerEntityDTO changeIsVacated(Long id, Boolean isVacated);

    WorkerEntityDTO changeIsVacatedIn3Months(Long id, Boolean isVacated);

    WorkerEntityDTO changeIsCategoryNextYear(Long id, Boolean isCategoryNextYear);

    WorkerEntityDTO changeIsEccNextMonth(Long id, Boolean isEccNextMonth);

    WorkerEntityDTO changeIsCategoryInNext3Months(Long id, Boolean isCategoryInNext3Months);

    WorkerEntityDTO changeIsQualificationNextMonth(Long id, Boolean isQualificationNextMonth);

}
