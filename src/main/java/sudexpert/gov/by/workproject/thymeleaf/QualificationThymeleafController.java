package sudexpert.gov.by.workproject.thymeleaf;


import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.CategoryDTO;
import sudexpert.gov.by.workproject.dto.QualificationDTO;
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.service.QualificationService;
import sudexpert.gov.by.workproject.service.WorkerEntityService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QualificationThymeleafController {

    QualificationService qualificationService;
    WorkerEntityService workerEntityService;

    @GetMapping("/workers/view/{workerId}/qualifications")
    public String viewQualification(@PathVariable Long workerId,
                             @RequestParam(required = false, defaultValue = "asc") String sort,
                             Model model) {

        WorkerEntityDTO worker = workerEntityService.getWorkerEntityById(workerId);
        if (worker == null) {
            return "redirect:/workers/view";
        }

        List<QualificationDTO> qualifications = qualificationService.getQualificationsByWorkerId(workerId);
        if (qualifications == null) qualifications = new ArrayList<>();
        else qualifications = new ArrayList<>(qualifications);

        // сортировка по дате начала
        if ("asc".equalsIgnoreCase(sort)) {
            qualifications.sort(Comparator.comparing(
                    QualificationDTO::start,
                    Comparator.nullsLast(Comparator.naturalOrder())
            ));
        } else if ("desc".equalsIgnoreCase(sort)) {
            qualifications.sort(Comparator.comparing(
                    QualificationDTO::start,
                    Comparator.nullsLast(Comparator.reverseOrder())
            ));
        }

        model.addAttribute("worker", worker);
        model.addAttribute("qualifications", qualifications);
        model.addAttribute("sort", sort);

        return "qualifications";
    }


    @GetMapping("/workers/view/{workerId}/qualifications/add")
    public String showAddForm(@PathVariable Long workerId, Model model) {
        QualificationDTO qualificationDTO = new QualificationDTO(null, workerId, "", null, null, "");
        model.addAttribute("qualification", qualificationDTO);
        return "qualifications-add";
    }

    @PostMapping("/workers/view/{workerId}/qualifications/add")
    public String addQualification(@PathVariable Long workerId,
                           @ModelAttribute("qualification") @Valid QualificationDTO qualificationDTO) {

        QualificationDTO dtoToSave = new QualificationDTO(
                null,
                workerId,
                qualificationDTO.title(),
                qualificationDTO.start(),
                qualificationDTO.end(),
                qualificationDTO.description()
        );

        qualificationService.createQualification(dtoToSave);
        return "redirect:/workers/view/" + workerId + "/qualifications";
    }


    // Форма редактирования категории
    @GetMapping("/workers/view/{workerId}/qualifications/{qualificationId}/edit")
    public String showEditForm(@PathVariable Long workerId,
                               @PathVariable Long qualificationId,
                               Model model) {

        QualificationDTO qualification = qualificationService.getQualificationById(qualificationId);
        model.addAttribute("qualification", qualification);
        model.addAttribute("workerId", workerId);
        return "qualifications-edit";
    }

    @PostMapping("/workers/view/{workerId}/qualifications/{qualificationId}/edit")
    public String editQualification(@PathVariable Long workerId,
                               @PathVariable Long qualificationId,
                               @ModelAttribute("qualification") @Valid QualificationDTO qualificationDTO) {

        QualificationDTO dtoToUpdate = new QualificationDTO(
                qualificationId,
                workerId,
                qualificationDTO.title(),
                qualificationDTO.start(),
                qualificationDTO.end(),
                qualificationDTO.description()
        );

        qualificationService.updateQualification(qualificationId, dtoToUpdate);
        return "redirect:/workers/view/" + workerId + "/qualifications";
    }

    @PostMapping("/workers/view/{workerId}/qualifications/{qualificationId}/delete")
    public String deleteQualification(@PathVariable Long workerId,
                                 @PathVariable Long qualificationId) {

        qualificationService.deleteQualification(qualificationId);
        return "redirect:/workers/view/" + workerId + "/qualifications";
    }

    @PostMapping("/workers/view/{workerId}/qualifications/{qualificationId}/save")
    public String saveEditedQualification(
            @PathVariable Long workerId,
            @PathVariable Long qualificationId,
            @ModelAttribute("qualification") @Valid QualificationDTO qualificationDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("qualification", qualificationDTO);
            return "qualifications"; // возвращаем шаблон редактирования
        }

        qualificationService.updateQualification(qualificationId, qualificationDTO);

        return "redirect:/workers/view/" + workerId + "/qualifications";
    }

}
