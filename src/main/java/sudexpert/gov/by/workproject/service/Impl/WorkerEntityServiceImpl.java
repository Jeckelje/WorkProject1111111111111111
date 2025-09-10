package sudexpert.gov.by.workproject.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.exception.ResourceNotFoundException;
import sudexpert.gov.by.workproject.mapper.WorkerEntityMapper;
import sudexpert.gov.by.workproject.model.WorkerEntity;
import sudexpert.gov.by.workproject.repository.WorkerEntityRepository;
import sudexpert.gov.by.workproject.service.WorkerEntityService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerEntityServiceImpl implements WorkerEntityService {

    private final WorkerEntityMapper workerEntityMapper;
    private final WorkerEntityRepository workerEntityRepository;

    @Override
    public WorkerEntityDTO createWorkerEntity(WorkerEntityDTO workerEntityDTO) {
        return workerEntityMapper.toDTO(workerEntityRepository.save(workerEntityMapper.toEntity(workerEntityDTO)));
    }

    @Override
    public WorkerEntityDTO updateWorkerEntity(Long id, WorkerEntityDTO workerEntityDTO) {
        WorkerEntity workerEntity = workerEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        workerEntityMapper.updateWorkerEntityFromRequest(workerEntityDTO, workerEntity);

        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));
    }

    @Override
    public WorkerEntityDTO getWorkerEntityById(Long id) {
        return workerEntityMapper.toDTO(workerEntityRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException("")));
    }

    @Override
    public List<WorkerEntityDTO> getAllWorkers() {
        return workerEntityRepository.findAll().stream()
                .map(workerEntityMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteWorkerEntity(Long id) {
        workerEntityRepository.deleteById(id);
    }

    @Override
    public WorkerEntityDTO changeIsVacated(Long id, Boolean isVacated) {
        WorkerEntity workerEntity = workerEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        workerEntity.setVacated(isVacated);
        workerEntityMapper.updateWorkerEntityFromRequest(workerEntity, workerEntity);

        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));
    }

    @Override
    public WorkerEntityDTO changeIsVacatedIn3Months(Long id, Boolean isVacated) {
        WorkerEntity workerEntity = workerEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        workerEntity.setVacatedIn3Months(isVacated);
        workerEntityMapper.updateWorkerEntityFromRequest(workerEntity, workerEntity);

        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));    }

    @Override
    public WorkerEntityDTO changeIsCategoryNextYear(Long id, Boolean isCategoryNextYear) {
        WorkerEntity workerEntity = workerEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        workerEntity.setCategoryNextYear(isCategoryNextYear);
        workerEntityMapper.updateWorkerEntityFromRequest(workerEntity, workerEntity);

        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));    }

    @Override
    public WorkerEntityDTO changeIsEccNextMonth(Long id, Boolean isEccNextMonth) {
        WorkerEntity workerEntity = workerEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        workerEntity.setEccNextMonth(isEccNextMonth);
        workerEntityMapper.updateWorkerEntityFromRequest(workerEntity, workerEntity);

        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));
    }

    @Override
    public WorkerEntityDTO changeIsCategoryInNext3Months(Long id, Boolean isCategoryInNext3Months) {
        WorkerEntity workerEntity = workerEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        workerEntity.setCategoryNext3Month(isCategoryInNext3Months);
        workerEntityMapper.updateWorkerEntityFromRequest(workerEntity, workerEntity);

        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));
    }

    @Override
    public WorkerEntityDTO changeIsQualificationNextMonth(Long id, Boolean isQualificationNextMonth) {
        WorkerEntity workerEntity = workerEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        workerEntity.setQualificationNextMonth(isQualificationNextMonth);
        workerEntityMapper.updateWorkerEntityFromRequest(workerEntity, workerEntity);

        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));
    }


}
