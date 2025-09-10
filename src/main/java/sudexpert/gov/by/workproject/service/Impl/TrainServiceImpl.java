package sudexpert.gov.by.workproject.service.Impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sudexpert.gov.by.workproject.dto.TrainDTO;
import sudexpert.gov.by.workproject.error.ErrorMessages;
import sudexpert.gov.by.workproject.exception.ResourceNotFoundException;
import sudexpert.gov.by.workproject.mapper.TrainMapper;
import sudexpert.gov.by.workproject.model.Train;
import sudexpert.gov.by.workproject.repository.TrainRepository;
import sudexpert.gov.by.workproject.service.TrainService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainMapper trainMapper;
    private final TrainRepository trainRepository;


    @Override
    public TrainDTO createTrain(TrainDTO trainDTO) {
        return trainMapper.toDTO(trainRepository.save(trainMapper.toEntity(trainDTO)));
    }

    @Override
    public TrainDTO updateTrain(Long id, TrainDTO trainDTO) {
        Train existingTrain = findTrainByIdOrThrow(id);
        trainMapper.updateTrainFromRequest(trainDTO, existingTrain);

        Train updatedTrain = trainRepository.save(existingTrain);
        return trainMapper.toDTO(updatedTrain);
    }

    @Override
    public TrainDTO getTrainById(Long id) {
        Train train = findTrainByIdOrThrow(id);
        return trainMapper.toDTO(train);
    }

    @Override
    public void deleteTrain(Long id) {
        Train train = findTrainByIdOrThrow(id);
        trainRepository.deleteById(id);
    }

    @Override
    public List<TrainDTO> getAllTrains() {
        return trainRepository.findAll().stream()
                .map(trainMapper::toDTO)
                .toList();
    }

    @Override
    public List<TrainDTO> getTrainsByWorkerId(Long workerId) {
        List<Train> trains = findTrainsByWorkerIdOrThrow(workerId);
        return trains.stream()
                .map(trainMapper::toDTO).toList();
    }

    //---------------------------------------------------------------------------------------------------------
    private Train findTrainByIdOrThrow(Long id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_ID_MESSAGE, "Train", id)));
    }

    private List<Train> findTrainsByWorkerIdOrThrow(Long workerId) {
        List<Train> trains = trainRepository.findTrainsByWorkerId(workerId);
        if (trains.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_CUSTOMER_NAME_MESSAGE, "Train for worker", workerId)
            );
        }
        return trains;
    }
}
