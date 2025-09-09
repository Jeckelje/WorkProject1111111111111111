package sudexpert.gov.by.workproject.service;

import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;

import java.util.List;

public interface WorkerEntityService {

    WorkerEntityDTO createWorkerEntity(WorkerEntityDTO workerEntityDTO);

    WorkerEntityDTO updateWorkerEntity(Long id, WorkerEntityDTO workerEntityDTO);

    WorkerEntityDTO getWorkerEntityById(Long id);

    List<WorkerEntityDTO> getAllWorkers();

    void deleteWorkerEntity(Long id);

}
