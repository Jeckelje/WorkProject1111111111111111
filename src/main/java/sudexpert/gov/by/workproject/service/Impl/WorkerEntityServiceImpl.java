package sudexpert.gov.by.workproject.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.exception.ResourceNotFoundException;
import sudexpert.gov.by.workproject.mapper.WorkerEntityMapper;
import sudexpert.gov.by.workproject.model.WorkerEntity;
import sudexpert.gov.by.workproject.repository.WorkerEntityRepository;
import sudexpert.gov.by.workproject.service.WorkerEntityService;


import java.util.List;

import static sudexpert.gov.by.workproject.specifications.WorkerSpecifications.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WorkerEntityServiceImpl implements WorkerEntityService {

    WorkerEntityMapper workerEntityMapper;
    WorkerEntityRepository workerEntityRepository;

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
                .orElseThrow(() -> new ResourceNotFoundException("")));
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
        workerEntity.setIsVacated(isVacated);
        WorkerEntityDTO workerEntityDTO = workerEntityMapper.toDTO(workerEntity);
        //return  workerEntityMapper.toDTO(workerEntityRepository.save(workerEntity));
        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));
    }

    @Override
    public WorkerEntityDTO changeIsVacatedIn3Months(Long id, Boolean isVacated) {
        WorkerEntity workerEntity = workerEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        workerEntity.setIsVacatedIn3Months(isVacated);
        WorkerEntityDTO workerEntityDTO = workerEntityMapper.toDTO(workerEntity);
        workerEntityMapper.updateWorkerEntityFromRequest(workerEntityDTO, workerEntity);

        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));
    }

    @Override
    public WorkerEntityDTO changeIsCategoryNextYear(Long id, Boolean isCategoryNextYear) {
        WorkerEntity workerEntity = workerEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        workerEntity.setIsCategoryNextYear(isCategoryNextYear);
        WorkerEntityDTO workerEntityDTO = workerEntityMapper.toDTO(workerEntity);
        workerEntityMapper.updateWorkerEntityFromRequest(workerEntityDTO, workerEntity);

        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));
    }

    @Override
    public WorkerEntityDTO changeIsEccNextMonth(Long id, Boolean isEccNextMonth) {
        WorkerEntity workerEntity = workerEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        workerEntity.setIsEccNextMonth(isEccNextMonth);
        WorkerEntityDTO workerEntityDTO = workerEntityMapper.toDTO(workerEntity);
        workerEntityMapper.updateWorkerEntityFromRequest(workerEntityDTO, workerEntity);

        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));
    }

    @Override
    public WorkerEntityDTO changeIsCategoryInNext3Months(Long id, Boolean isCategoryInNext3Months) {
        WorkerEntity workerEntity = workerEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        workerEntity.setIsCategoryNext3Month(isCategoryInNext3Months);
        WorkerEntityDTO workerEntityDTO = workerEntityMapper.toDTO(workerEntity);
        workerEntityMapper.updateWorkerEntityFromRequest(workerEntityDTO, workerEntity);

        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));
    }

    @Override
    public WorkerEntityDTO changeIsQualificationNextMonth(Long id, Boolean isQualificationNextMonth) {
        WorkerEntity workerEntity = workerEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        workerEntity.setIsQualificationNextMonth(isQualificationNextMonth);
        WorkerEntityDTO workerEntityDTO = workerEntityMapper.toDTO(workerEntity);
        workerEntityMapper.updateWorkerEntityFromRequest(workerEntityDTO, workerEntity);

        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));
    }

    @Override
    public WorkerEntityDTO changeIsBday5(Long id, Boolean isBday5) {

        WorkerEntity workerEntity = workerEntityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker with id " + id + " not found"));
        workerEntity.setIsBday5(isBday5);
        WorkerEntityDTO workerEntityDTO = workerEntityMapper.toDTO(workerEntity);
        workerEntityMapper.updateWorkerEntityFromRequest(workerEntityDTO, workerEntity);

        return workerEntityMapper.toDTO((workerEntityRepository.save(workerEntity)));
    }

    @Override
    public List<WorkerEntityDTO> getFilteredWorkers(String search, String sort, String job) {
        Specification<WorkerEntity> spec = Specification.where(hasNameLike(search))
                .and(hasJobTitle(job));

        Sort sortObj = Sort.unsorted();

        if ("surname".equals(sort)) {
            sortObj = Sort.by("surname");
        } else if ("jobTitle".equals(sort)) {
            sortObj = Sort.by("jobTitle");
        } else if ("birthDay".equals(sort)) {
            sortObj = Sort.by("birthDay");
        } else if ("isVacated".equals(sort)) {
            spec = spec.and(hasFlag("isVacated"));
        } else if ("isVacatedIn3Months".equals(sort)) {
            spec = spec.and(hasFlag("isVacatedIn3Months"));
        } else if ("isCategoryNextYear".equals(sort)) {
            spec = spec.and(hasFlag("isCategoryNextYear"));
        } else if ("isTrainNextMonth".equals(sort)) {
            spec = spec.and(hasFlag("isTrainNextMonth"));
        } else if ("isEccNextMonth".equals(sort)) {
            spec = spec.and(hasFlag("isEccNextMonth"));
        } else if ("isQualificationNextMonth".equals(sort)) {
            spec = spec.and(hasFlag("isQualificationNextMonth"));
        } else if ("isCategoryNext3Month".equals(sort)) {
            spec = spec.and(hasFlag("isCategoryNext3Month"));
        } else if ("isBday5".equals(sort)) {
            spec = spec.and(hasFlag("isBday5"));
        }

        return workerEntityRepository.findAll(spec, sortObj)
                .stream()
                .map(workerEntityMapper::toDTO)
                .toList();
    }

}
