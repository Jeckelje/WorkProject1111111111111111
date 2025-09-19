package sudexpert.gov.by.workproject.thymeleaf;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.AchievementDTO;
import sudexpert.gov.by.workproject.dto.VacationDTO;
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.service.VacationService;
import sudexpert.gov.by.workproject.service.WorkerEntityService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VacationsThymeleafController {

    VacationService vacationService;
    WorkerEntityService workerEntityService;

    // Просмотр отпусков
    @GetMapping("/workers/view/{workerId}/vacations")
    public String viewVacations(@PathVariable Long workerId,
                                @RequestParam(required = false, defaultValue = "asc") String sort,
                                Model model) {

        WorkerEntityDTO worker = workerEntityService.getWorkerEntityById(workerId);
        if (worker == null) {
            return "redirect:/workers/view";
        }

        List<VacationDTO> vacations = vacationService.getVacationsByWorkerId(workerId);
        if (vacations == null) vacations = new ArrayList<>();
        else vacations = new ArrayList<>(vacations);

        // сортировка по дате начала
        if ("asc".equalsIgnoreCase(sort)) {
            vacations.sort(Comparator.comparing(
                    VacationDTO::start,
                    Comparator.nullsLast(Comparator.naturalOrder())
            ));
        } else if ("desc".equalsIgnoreCase(sort)) {
            vacations.sort(Comparator.comparing(
                    VacationDTO::start,
                    Comparator.nullsLast(Comparator.reverseOrder())
            ));
        }

        model.addAttribute("worker", worker);
        model.addAttribute("vacations", vacations);
        model.addAttribute("sort", sort);

        return "vacations";
    }

    // Форма добавления нового отпуска
    @GetMapping("/workers/view/{workerId}/vacations/add")
    public String showAddForm(@PathVariable Long workerId, Model model) {
        VacationDTO vacationDTO = new VacationDTO(null, workerId, "", null, null, "");
        model.addAttribute("vacation", vacationDTO);
        return "vacations-add";
    }

    @PostMapping("/workers/view/{workerId}/vacations/add")
    public String addVacation(@PathVariable Long workerId,
                              @ModelAttribute("vacation") @Valid VacationDTO vacationDTO) {

        VacationDTO dtoToSave = new VacationDTO(
                null,
                workerId,
                vacationDTO.title(),
                vacationDTO.start(),
                vacationDTO.end(),
                vacationDTO.description()
        );

        vacationService.createVacation(dtoToSave);
        return "redirect:/workers/view/" + workerId + "/vacations";
    }

    // Форма редактирования отпуска
    @GetMapping("/workers/view/{workerId}/vacations/{vacationId}/edit")
    public String showEditForm(@PathVariable Long workerId,
                               @PathVariable Long vacationId,
                               Model model) {

        VacationDTO vacation = vacationService.getVacationById(vacationId);
        model.addAttribute("vacation", vacation);
        model.addAttribute("workerId", workerId);
        return "vacations-edit";
    }

    @PostMapping("/workers/view/{workerId}/vacations/{vacationId}/edit")
    public String editVacation(@PathVariable Long workerId,
                               @PathVariable Long vacationId,
                               @ModelAttribute("vacation") @Valid VacationDTO vacationDTO) {

        VacationDTO dtoToUpdate = new VacationDTO(
                vacationId,
                workerId,
                vacationDTO.title(),
                vacationDTO.start(),
                vacationDTO.end(),
                vacationDTO.description()
        );

        vacationService.updateVacation(vacationId, dtoToUpdate);
        return "redirect:/workers/view/" + workerId + "/vacations";
    }

    // Удаление отпуска
    @PostMapping("/workers/view/{workerId}/vacations/{vacationId}/delete")
    public String deleteVacation(@PathVariable Long workerId,
                                 @PathVariable Long vacationId) {

        vacationService.deleteVacation(vacationId);
        return "redirect:/workers/view/" + workerId + "/vacations";
    }

    @PostMapping("/workers/view/{workerId}/vacations/{vacationId}/save")
    public String saveEditedVacation(
            @PathVariable Long workerId,
            @PathVariable Long vacationId,
            @ModelAttribute("vacation") @Valid VacationDTO vacationDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            // Если есть ошибки валидации, возвращаем форму редактирования
            model.addAttribute("vacation", vacationDTO);
            return "vacations"; // имя Thymeleaf-шаблона редактирования
        }

        // Используем готовый метод из сервиса
        vacationService.updateVacation(vacationId, vacationDTO);

        // Редирект обратно на список достижений сотрудника
        return "redirect:/workers/view/" + workerId + "/vacations";
    }
}
