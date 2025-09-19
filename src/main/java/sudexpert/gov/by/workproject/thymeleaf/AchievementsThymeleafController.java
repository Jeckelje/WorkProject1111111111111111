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
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.service.AchievementService;
import sudexpert.gov.by.workproject.service.WorkerEntityService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AchievementsThymeleafController {

    AchievementService achievementService;
    WorkerEntityService workerEntityService;

    // Просмотр всех достижений
    @GetMapping("/workers/view/{workerId}/achievements")
    public String viewAchievements(@PathVariable Long workerId,
                                   @RequestParam(required = false, defaultValue = "asc") String sort,
                                   Model model) {

        WorkerEntityDTO worker = workerEntityService.getWorkerEntityById(workerId);
        if (worker == null) {
            return "redirect:/workers/view";
        }

        List<AchievementDTO> achievements = achievementService.getAchievementsByWorkerId(workerId);
        if (achievements == null) achievements = new ArrayList<>();
        else achievements = new ArrayList<>(achievements);

        // сортировка по дате
        if ("asc".equalsIgnoreCase(sort)) {
            achievements.sort(Comparator.comparing(
                    AchievementDTO::date,
                    Comparator.nullsLast(Comparator.naturalOrder())
            ));
        } else if ("desc".equalsIgnoreCase(sort)) {
            achievements.sort(Comparator.comparing(
                    AchievementDTO::date,
                    Comparator.nullsLast(Comparator.reverseOrder())
            ));
        }

        model.addAttribute("worker", worker);
        model.addAttribute("achievements", achievements);
        model.addAttribute("sort", sort);

        return "achievements";
    }


    // Добавление нового достижения
    @GetMapping("/workers/view/{workerId}/achievements/add")
    public String showAddForm(@PathVariable Long workerId, Model model) {
        AchievementDTO achievementDTO = new AchievementDTO(null, workerId, "", null);
        model.addAttribute("achievement", achievementDTO);
        return "achievements-add";
    }

    //Нажатие на кнопку "Добавить"
    @PostMapping("/workers/view/{workerId}/achievements/add")
    public String addAchievement(@PathVariable Long workerId,
                                 @ModelAttribute("achievement") AchievementDTO achievementDTO) {
        AchievementDTO dtoToSave = new AchievementDTO(
                null,
                workerId,
                achievementDTO.description(),
                achievementDTO.date()
        );
        achievementService.createAchievement(dtoToSave);
        return "redirect:/workers/view/" + workerId + "/achievements";
    }

    // Редактирование достижения
    @GetMapping("/workers/view/{workerId}/achievements/{achievementId}/edit")
    public String showEditForm(@PathVariable Long workerId,
                               @PathVariable Long achievementId,
                               Model model) {
        AchievementDTO achievement = achievementService.getAchievementById(achievementId);
        model.addAttribute("achievement", achievement);
        model.addAttribute("workerId", workerId);
        return "achievements-edit";
    }


    @PostMapping("/workers/view/{workerId}/achievements/{achievementId}/edit")
    public String editAchievement(@PathVariable Long workerId,
                                  @PathVariable Long achievementId,
                                  @ModelAttribute("achievement") AchievementDTO achievementDTO) {
        AchievementDTO dtoToUpdate = new AchievementDTO(
                achievementId,
                workerId,
                achievementDTO.description(),
                achievementDTO.date()
        );
        achievementService.updateAchievement(achievementId, dtoToUpdate);
        return "redirect:/workers/view/" + workerId + "/achievements";
    }

    // Удаление достижения
    @PostMapping("/workers/view/{workerId}/achievements/{achievementId}/delete")
    public String deleteAchievement(@PathVariable Long workerId,
                                    @PathVariable Long achievementId) {
        achievementService.deleteAchievement(achievementId);
        return "redirect:/workers/view/" + workerId + "/achievements";
    }

    @PostMapping("/workers/view/{workerId}/achievements/{achievementId}/save")
    public String saveEditedAchievement(
            @PathVariable Long workerId,
            @PathVariable Long achievementId,
            @ModelAttribute("achievement") @Valid AchievementDTO achievementDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            // Если есть ошибки валидации, возвращаем форму редактирования
            model.addAttribute("achievement", achievementDTO);
            return "achievement"; // имя Thymeleaf-шаблона редактирования
        }

        // Используем готовый метод из сервиса
        achievementService.updateAchievement(achievementId, achievementDTO);

        // Редирект обратно на список достижений сотрудника
        return "redirect:/workers/view/" + workerId + "/achievements";
    }
}
