package sudexpert.gov.by.workproject.thymeleaf;


import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.ECCDTO;
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.service.ECCService;
import sudexpert.gov.by.workproject.service.WorkerEntityService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EccThymeleafController {

    ECCService eccService;
    WorkerEntityService workerEntityService;

    @GetMapping("/workers/view/{workerId}/eccs")
    public String viewEccs(@PathVariable Long workerId,
                           @RequestParam(required = false, defaultValue = "asc") String sort,
                           Model model) {

        WorkerEntityDTO worker = workerEntityService.getWorkerEntityById(workerId);
        if (worker == null) {
            return "redirect:/workers/view";
        }

        List<ECCDTO> eccs = eccService.getEccsByWorkerId(workerId);
        if (eccs == null) eccs = new ArrayList<>();
        else eccs = new ArrayList<>(eccs);

        // сортировка по дате начала
        if ("asc".equalsIgnoreCase(sort)) {
            eccs.sort(Comparator.comparing(
                    ECCDTO::start,
                    Comparator.nullsLast(Comparator.naturalOrder())
            ));
        } else if ("desc".equalsIgnoreCase(sort)) {
            eccs.sort(Comparator.comparing(
                    ECCDTO::start,
                    Comparator.nullsLast(Comparator.reverseOrder())
            ));
        }

        model.addAttribute("worker", worker);
        model.addAttribute("eccs", eccs);
        model.addAttribute("sort", sort);

        return "eccs";
    }

    // Форма добавления новой стажировки
    @GetMapping("/workers/view/{workerId}/eccs/add")
    public String showAddForm(@PathVariable Long workerId, Model model) {
        ECCDTO eccdto = new ECCDTO(null, workerId, "", null, null, "");
        model.addAttribute("ecc", eccdto);
        return "eccs-add";
    }

    @PostMapping("/workers/view/{workerId}/eccs/add")
    public String addEcc(@PathVariable Long workerId,
                         @ModelAttribute("ecc") @Valid ECCDTO eccdto) {

        ECCDTO dtoToSave = new ECCDTO(
                null,
                workerId,
                eccdto.title(),
                eccdto.start(),
                eccdto.end(),
                eccdto.description()
        );

        eccService.createEcc(dtoToSave);
        return "redirect:/workers/view/" + workerId + "/eccs";
    }

    // Форма редактирования категории
    @GetMapping("/workers/view/{workerId}/eccs/{eccId}/edit")
    public String showEditForm(@PathVariable Long workerId,
                               @PathVariable Long eccId,
                               Model model) {

        ECCDTO ecc = eccService.getEccById(eccId);
        model.addAttribute("ecc", ecc);
        model.addAttribute("workerId", workerId);
        return "eccs-edit";
    }

    @PostMapping("/workers/view/{workerId}/eccs/{eccId}/edit")
    public String editEcc(@PathVariable Long workerId,
                          @PathVariable Long eccId,
                          @ModelAttribute("ecc") @Valid ECCDTO eccdto) {

        ECCDTO dtoToUpdate = new ECCDTO(
                eccId,
                workerId,
                eccdto.title(),
                eccdto.start(),
                eccdto.end(),
                eccdto.description()
        );

        eccService.updateEcc(eccId, dtoToUpdate);
        return "redirect:/workers/view/" + workerId + "/eccs";
    }

    // Удаление стажировки
    @PostMapping("/workers/view/{workerId}/eccs/{eccId}/delete")
    public String deleteEcc(@PathVariable Long workerId,
                            @PathVariable Long eccId) {

        eccService.deleteEcc(eccId);
        return "redirect:/workers/view/" + workerId + "/eccs";
    }

    @PostMapping("/workers/view/{workerId}/eccs/{eccId}/save")
    public String saveEditedEcc(
            @PathVariable Long workerId,
            @PathVariable Long eccId,
            @ModelAttribute("ecc") @Valid ECCDTO eccdto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ecc", eccdto);
            return "eccs"; // возвращаем шаблон редактирования
        }

        eccService.updateEcc(eccId, eccdto);

        return "redirect:/workers/view/" + workerId + "/eccs";
    }

}
