package sudexpert.gov.by.workproject.service;

import sudexpert.gov.by.workproject.dto.QualificationDTO;

import java.util.List;

public interface QualificationService {

    QualificationDTO createQualification(QualificationDTO qualificationDTO);

    QualificationDTO updateQualification(Long id, QualificationDTO qualificationDTO);

    QualificationDTO getQualificationById(Long id);

    void deleteQualification(Long id);

    List<QualificationDTO> getAllQualifications();

    List<QualificationDTO> getQualificationsByWorkerId(Long workerId);

}
