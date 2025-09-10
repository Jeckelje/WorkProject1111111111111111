package sudexpert.gov.by.workproject.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.TrainDTO;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;
import sudexpert.gov.by.workproject.service.TrainService;
import sudexpert.gov.by.workproject.swagger.TrainAPI;

import java.util.List;


@RestController
@RequestMapping("api/train")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainController implements TrainAPI {

    TrainService trainService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public TrainDTO createTrain(@RequestBody @Validated(OnCreate.class) TrainDTO trainDTO) {
        return trainService.createTrain(trainDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public TrainDTO updateTrain(
            @PathVariable Long id,
            @RequestBody @Validated(OnUpdate.class) TrainDTO trainDTO) {
        return trainService.updateTrain(id, trainDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void deleteTrain(@PathVariable Long id) {
        trainService.deleteTrain(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public TrainDTO getTrainById(@PathVariable Long id) {
        return trainService.getTrainById(id);
    }

    @GetMapping("/worker/{workerId}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<TrainDTO> getTrainsByWorkerId(@PathVariable Long workerId) {
        return trainService.getTrainsByWorkerId(workerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<TrainDTO> getAllTrains() {
        return trainService.getAllTrains();
    }
}
