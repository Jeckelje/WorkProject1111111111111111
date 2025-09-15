package sudexpert.gov.by.workproject.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sudexpert.gov.by.workproject.service.WorkerEntityService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
public class WorkerEntityThymeleafController {

    WorkerEntityService workerEntityService;

    /**
     * Страница со списком сотрудников (карточки)
     */
    @GetMapping("/workers/view")
    public String viewAllWorkers(Model model) {
        model.addAttribute("workers", workerEntityService.getAllWorkers());
        return "workers"; // workers.html
    }

    /**
     * Страница одного сотрудника
     */
    @GetMapping("/workers/view/{id}")
    public String viewWorkerDetail(@PathVariable Long id, Model model) {
        model.addAttribute("worker", workerEntityService.getWorkerEntityById(id));
        return "worker-detail"; // worker-detail.html
    }
}
