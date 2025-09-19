package sudexpert.gov.by.workproject.thymeleaf;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.AchievementDTO;
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.mapper.WorkerEntityMapper;
import sudexpert.gov.by.workproject.service.AchievementService;
import sudexpert.gov.by.workproject.service.CategoryService;
import sudexpert.gov.by.workproject.service.WorkerEntityService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
public class WorkerEntityThymeleafController {

    WorkerEntityService workerEntityService;
    CategoryService categoryService;

    /**
     * Страница со списком сотрудников (карточки)
     */
    @GetMapping("/workers/view")
    public String viewAllWorkers(@RequestParam(required = false) String search,
                                 @RequestParam(required = false) String sort,
                                 @RequestParam(required = false) String job,
                                 @RequestParam(required = false) String department,
                                 Model model) {

        List<WorkerEntityDTO> workers = workerEntityService.getFilteredWorkers(search, sort, job, department);
        model.addAttribute("workers", workers);

        Map<Long, LocalDate> lastCategoryEndDates = new HashMap<>();
        for (WorkerEntityDTO w : workers) {
            var lastCategory = categoryService.getLastCategoryForWorker(w.id());
            lastCategoryEndDates.put(w.id(), lastCategory != null ? lastCategory.end() : null);
        }
        model.addAttribute("lastCategoryEndDates", lastCategoryEndDates);

        // Чтобы при возврате на страницу фильтры сохранялись
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("job", job);
        model.addAttribute("department", department);

        return "workers"; // workers.html
    }


    /**
     * Страница одного сотрудника
     */
    @GetMapping("/workers/view/{workerId}")
    public String viewWorkerDetail(@PathVariable Long workerId, Model model) {
        var worker = workerEntityService.getWorkerEntityById(workerId);
        model.addAttribute("worker", worker);

        // Получаем последнюю категорию через сервис
        var lastCategory = categoryService.getLastCategoryForWorker(workerId);
        model.addAttribute("lastCategoryEndDate", lastCategory != null ? lastCategory.end() : null);

        return "worker-detail"; // worker-detail.html
    }

    // Отображение формы добавления нового сотрудника
    @GetMapping("/workers/add")
    public String addWorkerForm(Model model) {
        model.addAttribute("worker", new WorkerEntityDTO(
                null,
                "",
                "",
                "",
                "",
                LocalDate.now(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "" // department
        ));
        return "worker-form"; // worker-form.html
    }

    // Сохранение нового сотрудника
    @PostMapping("/workers/save")
    public String saveWorker(@ModelAttribute("worker") WorkerEntityDTO workerDTO) {
        workerEntityService.createWorkerEntity(workerDTO); // Используем существующий метод
        return "redirect:/workers/view";
    }

    // Отобразить форму редактирования
    @GetMapping("/workers/edit/{workerId}")
    public String editWorkerForm(@PathVariable Long workerId, Model model) {
        WorkerEntityDTO worker = workerEntityService.getWorkerEntityById(workerId);
        model.addAttribute("worker", worker);
        return "worker-form"; // используем ту же форму, что и для добавления
    }

    // Сохранение изменений после редактирования
    @PostMapping("/workers/edit/{workerId}")
    public String saveEditedWorker(@PathVariable Long workerId,
                                   @ModelAttribute("worker") WorkerEntityDTO workerDTO) {
        workerEntityService.updateWorkerEntity(workerId, workerDTO);
        return "redirect:/workers/view/" + workerId;
    }

    // Удаление сотрудника
    @PostMapping("/workers/delete/{workerId}")
    public String deleteWorker(@PathVariable Long workerId) {
        workerEntityService.deleteWorkerEntity(workerId);
        return "redirect:/workers/view";
    }

}
