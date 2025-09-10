package sudexpert.gov.by.workproject.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import sudexpert.gov.by.workproject.dto.VacationDTO;
import sudexpert.gov.by.workproject.error.ErrorMessages;
import sudexpert.gov.by.workproject.exception.ResourceNotFoundException;
import sudexpert.gov.by.workproject.mapper.VacationMapper;
import sudexpert.gov.by.workproject.model.Vacation;
import sudexpert.gov.by.workproject.repository.VacationRepository;
import sudexpert.gov.by.workproject.service.VacationService;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VacationServiceImpl implements VacationService {

    VacationMapper vacationMapper;
    VacationRepository vacationRepository;


    @Override
    public VacationDTO createVacation(VacationDTO vacationDTO) {
        return vacationMapper.toDTO(vacationRepository.save(vacationMapper.toEntity(vacationDTO)));
    }

    @Override
    public VacationDTO updateVacation(Long id, VacationDTO vacationDTO) {
        Vacation existingVacation = findVacationByIdOrThrow(id);
        vacationMapper.updateVacationFromRequest(vacationDTO, existingVacation);

        Vacation updatedVacation = vacationRepository.save(existingVacation);
        return vacationMapper.toDTO(updatedVacation);
    }

    @Override
    public VacationDTO getVacationById(Long id) {
        Vacation vacation = findVacationByIdOrThrow(id);
        return vacationMapper.toDTO(vacation);
    }

    @Override
    public void deleteVacation(Long id) {
        Vacation vacation = findVacationByIdOrThrow(id);
        vacationRepository.deleteById(id);
    }

    @Override
    public List<VacationDTO> getAllVacations() {
        return vacationRepository.findAll().stream()
                .map(vacationMapper::toDTO)
                .toList();
    }

    @Override
    public List<VacationDTO> getVacationByWorkerId(Long workerId) {
        List<Vacation> vacations = findVacationsByWorkerIdOrThrow(workerId);
        return vacations.stream()
                .map(vacationMapper::toDTO).toList();
    }

    //---------------------------------------------------------------------------------------------------------
    private Vacation findVacationByIdOrThrow(Long id) {
        return vacationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_ID_MESSAGE, "Vacation", id)));
    }

    private List<Vacation> findVacationsByWorkerIdOrThrow(Long workerId) {
        List<Vacation> vacations = vacationRepository.findVacationsByWorkerId(workerId);
        if (vacations.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_CUSTOMER_NAME_MESSAGE, "Vacation for worker", workerId)
            );
        }
        return vacations;
    }
}
