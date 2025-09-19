package sudexpert.gov.by.workproject.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import sudexpert.gov.by.workproject.dto.VacationDTO;
import sudexpert.gov.by.workproject.error.ErrorMessages;
import sudexpert.gov.by.workproject.exception.ResourceNotFoundException;
import sudexpert.gov.by.workproject.mapper.VacationMapper;
import sudexpert.gov.by.workproject.mapper.WorkerEntityMapper;
import sudexpert.gov.by.workproject.model.Achievement;
import sudexpert.gov.by.workproject.model.Vacation;
import sudexpert.gov.by.workproject.repository.VacationRepository;
import sudexpert.gov.by.workproject.service.VacationService;
import sudexpert.gov.by.workproject.service.WorkerEntityService;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VacationServiceImpl implements VacationService {

    VacationMapper vacationMapper;
    VacationRepository vacationRepository;
    WorkerEntityService workerEntityService;
    WorkerEntityMapper workerEntityMapper;

    @Override
    public VacationDTO createVacation(VacationDTO vacationDTO) {
        Vacation vacation = vacationMapper.toEntity(vacationDTO);
        vacation.setWorker(workerEntityMapper.toEntity(workerEntityService.getWorkerEntityById(vacationDTO.workerId())));
        //train.setWorkerId(trainDTO.workerId());
        return vacationMapper.toDTO(vacationRepository.save(vacation));
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
    public List<VacationDTO> getVacationsByWorkerId(Long workerId) {
        List<Vacation> vacations = vacationRepository.findVacationsByWorkerId(workerId);
        return vacations.stream()
                .map(vacationMapper::toDTO).toList();
    }

    @Override
    public VacationDTO getNearestVacationForWorker(Long workerId) {
        List<Vacation> vacations = vacationRepository.findVacationsByWorkerId(workerId);

        return vacations.stream()
                .filter(v -> v.getStart() != null && v.getStart().isAfter(java.time.LocalDate.now()))
                .min((v1, v2) -> v1.getStart().compareTo(v2.getStart())) // ближайший по дате начала
                .map(vacationMapper::toDTO)
                .orElse(null);
    }

    @Override
    public VacationDTO getNowOnVacationForWorker(Long workerId) {
        List<Vacation> vacations = vacationRepository.findVacationsByWorkerId(workerId);

        return vacations.stream()
                .filter(v -> v.getStart() != null && v.getEnd() != null
                        && !java.time.LocalDate.now().isBefore(v.getStart())
                        && !java.time.LocalDate.now().isAfter(v.getEnd()))
                .findFirst() // или min по дате начала, если нужно ближайший отпуск
                .map(vacationMapper::toDTO)
                .orElse(null);
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
