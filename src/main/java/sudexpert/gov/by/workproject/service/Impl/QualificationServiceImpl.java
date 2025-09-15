package sudexpert.gov.by.workproject.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import sudexpert.gov.by.workproject.dto.QualificationDTO;
import sudexpert.gov.by.workproject.error.ErrorMessages;
import sudexpert.gov.by.workproject.exception.ResourceNotFoundException;
import sudexpert.gov.by.workproject.mapper.QualificationMapper;
import sudexpert.gov.by.workproject.mapper.WorkerEntityMapper;
import sudexpert.gov.by.workproject.model.Qualification;
import sudexpert.gov.by.workproject.repository.QualificationRepository;
import sudexpert.gov.by.workproject.service.QualificationService;
import sudexpert.gov.by.workproject.service.WorkerEntityService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QualificationServiceImpl implements QualificationService {

    QualificationMapper qualificationMapper;
    QualificationRepository qualificationRepository;
    WorkerEntityService workerEntityService;
    WorkerEntityMapper workerEntityMapper;


    @Override
    public QualificationDTO createQualification(QualificationDTO qualificationDTO) {
        Qualification qualification = qualificationMapper.toEntity(qualificationDTO);
        qualification.setWorker(workerEntityMapper.toEntity(workerEntityService.getWorkerEntityById(qualificationDTO.workerId())));
        //train.setWorkerId(trainDTO.workerId());
        return qualificationMapper.toDTO(qualificationRepository.save(qualification));
    }

    @Override
    public QualificationDTO updateQualification(Long id, QualificationDTO qualificationDTO) {
        Qualification existingQualification = findQualificationByIdOrThrow(id);
        qualificationMapper.updateQualificationFromRequest(qualificationDTO, existingQualification);

        Qualification updatedQualification = qualificationRepository.save(existingQualification);
        return qualificationMapper.toDTO(updatedQualification);
    }

    @Override
    public QualificationDTO getQualificationById(Long id) {
        Qualification qualification = findQualificationByIdOrThrow(id);
        return qualificationMapper.toDTO(qualification);
    }

    @Override
    public void deleteQualification(Long id) {
        Qualification qualification = findQualificationByIdOrThrow(id);
        qualificationRepository.deleteById(id);
    }

    @Override
    public List<QualificationDTO> getAllQualifications() {
        return qualificationRepository.findAll().stream()
                .map(qualificationMapper::toDTO)
                .toList();
    }

    @Override
    public List<QualificationDTO> getQualificationsByWorkerId(Long workerId) {
        List<Qualification> qualifications = findQualificationsByWorkerIdOrThrow(workerId);
        return qualifications.stream()
                .map(qualificationMapper::toDTO).toList();
    }

    //---------------------------------------------------------------------------------------------------------
    private Qualification findQualificationByIdOrThrow(Long id) {
        return qualificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_ID_MESSAGE, "Qualification", id)));
    }

    private List<Qualification> findQualificationsByWorkerIdOrThrow(Long workerId) {
        List<Qualification> qualifications = qualificationRepository.findQualificationsByWorkerId(workerId);
        if (qualifications.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_CUSTOMER_NAME_MESSAGE, "Qualification for worker", workerId)
            );
        }
        return qualifications;
    }
}
