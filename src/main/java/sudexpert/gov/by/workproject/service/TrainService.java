package sudexpert.gov.by.workproject.service;

import sudexpert.gov.by.workproject.dto.TrainDTO;

import java.util.List;

public interface  TrainService {

    TrainDTO createTrain(TrainDTO trainDTO);

    TrainDTO updateTrain(Long id, TrainDTO trainDTO);

    TrainDTO getTrainById(Long id);

    void deleteTrain(Long id);

    List<TrainDTO> getAllTrains();

    List<TrainDTO> getTrainsByWorkerId(Long workerId);

}
