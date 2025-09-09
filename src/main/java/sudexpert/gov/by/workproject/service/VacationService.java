package sudexpert.gov.by.workproject.service;

import sudexpert.gov.by.workproject.dto.VacationDTO;

import java.util.List;

public interface VacationService {

    VacationDTO createVacation(VacationDTO vacationDTO);

    VacationDTO updateVacation(Long id, VacationDTO vacationDTO);

    VacationDTO getVacationById(Long id);

    void deleteVacation(Long id);

    List<VacationDTO> getAllVacations();

    List<VacationDTO> getVacationByWorkerId(Long workerId);

}
