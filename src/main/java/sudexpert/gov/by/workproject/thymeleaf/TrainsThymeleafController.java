package sudexpert.gov.by.workproject.thymeleaf;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.TrainDTO;
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.service.TrainService;
import sudexpert.gov.by.workproject.service.WorkerEntityService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainsThymeleafController {

    TrainService trainService;
    WorkerEntityService workerEntityService;

    // Просмотр стажировок
    @GetMapping("/workers/view/{workerId}/trains")
    public String viewTrains(@PathVariable Long workerId,
                             @RequestParam(required = false, defaultValue = "asc") String sort,
                             Model model) {

        WorkerEntityDTO worker = workerEntityService.getWorkerEntityById(workerId);
        if (worker == null) {
            return "redirect:/workers/view";
        }

        List<TrainDTO> trains = trainService.getTrainsByWorkerId(workerId);
        if (trains == null) trains = new ArrayList<>();
        else trains = new ArrayList<>(trains);

        // сортировка по дате начала
        if ("asc".equalsIgnoreCase(sort)) {
            trains.sort(Comparator.comparing(
                    TrainDTO::start,
                    Comparator.nullsLast(Comparator.naturalOrder())
            ));
        } else if ("desc".equalsIgnoreCase(sort)) {
            trains.sort(Comparator.comparing(
                    TrainDTO::start,
                    Comparator.nullsLast(Comparator.reverseOrder())
            ));
        }

        model.addAttribute("worker", worker);
        model.addAttribute("trains", trains);
        model.addAttribute("sort", sort);

        return "trains";
    }

    // Форма добавления новой стажировки
    @GetMapping("/workers/view/{workerId}/trains/add")
    public String showAddForm(@PathVariable Long workerId, Model model) {
        TrainDTO trainDTO = new TrainDTO(null, workerId, "", null, null, "");
        model.addAttribute("train", trainDTO);
        return "trains-add";
    }

    @PostMapping("/workers/view/{workerId}/trains/add")
    public String addTrain(@PathVariable Long workerId,
                           @ModelAttribute("train") @Valid TrainDTO trainDTO) {

        TrainDTO dtoToSave = new TrainDTO(
                null,
                workerId,
                trainDTO.title(),
                trainDTO.start(),
                trainDTO.end(),
                trainDTO.description()
        );

        trainService.createTrain(dtoToSave);
        return "redirect:/workers/view/" + workerId + "/trains";
    }

    // Форма редактирования стажировки
    @GetMapping("/workers/view/{workerId}/trains/{trainId}/edit")
    public String showEditForm(@PathVariable Long workerId,
                               @PathVariable Long trainId,
                               Model model) {

        TrainDTO train = trainService.getTrainById(trainId);
        model.addAttribute("train", train);
        model.addAttribute("workerId", workerId);
        return "trains-edit";
    }

    @PostMapping("/workers/view/{workerId}/trains/{trainId}/edit")
    public String editTrain(@PathVariable Long workerId,
                            @PathVariable Long trainId,
                            @ModelAttribute("train") @Valid TrainDTO trainDTO) {

        TrainDTO dtoToUpdate = new TrainDTO(
                trainId,
                workerId,
                trainDTO.title(),
                trainDTO.start(),
                trainDTO.end(),
                trainDTO.description()
        );

        trainService.updateTrain(trainId, dtoToUpdate);
        return "redirect:/workers/view/" + workerId + "/trains";
    }

    // Удаление стажировки
    @PostMapping("/workers/view/{workerId}/trains/{trainId}/delete")
    public String deleteTrain(@PathVariable Long workerId,
                              @PathVariable Long trainId) {

        trainService.deleteTrain(trainId);
        return "redirect:/workers/view/" + workerId + "/trains";
    }

    @PostMapping("/workers/view/{workerId}/trains/{trainId}/save")
    public String saveEditedTrain(
            @PathVariable Long workerId,
            @PathVariable Long trainId,
            @ModelAttribute("train") @Valid TrainDTO trainDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("train", trainDTO);
            return "trains"; // возвращаем шаблон редактирования
        }

        trainService.updateTrain(trainId, trainDTO);

        return "redirect:/workers/view/" + workerId + "/trains";
    }
}
