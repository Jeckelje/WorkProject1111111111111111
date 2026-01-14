package sudexpert.gov.by.workproject.thymeleaf;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.CategoryDTO;
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.service.CategoryService;
import sudexpert.gov.by.workproject.service.WorkerEntityService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryThymeleafController {

    CategoryService categoryService;
    WorkerEntityService workerEntityService;

    // Просмотр стажировок
    @GetMapping("/workers/view/{workerId}/categories")
    public String viewCategories(@PathVariable Long workerId,
                             @RequestParam(required = false, defaultValue = "asc") String sort,
                             Model model) {

        WorkerEntityDTO worker = workerEntityService.getWorkerEntityById(workerId);
        if (worker == null) {
            return "redirect:/workers/view";
        }

        List<CategoryDTO> categories = categoryService.getCategoriesByWorkerId(workerId);
        if (categories == null) categories = new ArrayList<>();
        else categories = new ArrayList<>(categories);

        // сортировка по дате начала
        if ("asc".equalsIgnoreCase(sort)) {
            categories.sort(Comparator.comparing(
                    CategoryDTO::start,
                    Comparator.nullsLast(Comparator.naturalOrder())
            ));
        } else if ("desc".equalsIgnoreCase(sort)) {
            categories.sort(Comparator.comparing(
                    CategoryDTO::start,
                    Comparator.nullsLast(Comparator.reverseOrder())
            ));
        }

        model.addAttribute("worker", worker);
        model.addAttribute("categories", categories);
        model.addAttribute("sort", sort);

        return "categories";
    }


    // Форма добавления новой стажировки
    @GetMapping("/workers/view/{workerId}/categories/add")
    public String showAddForm(@PathVariable Long workerId, Model model) {
        CategoryDTO categoryDTO = new CategoryDTO(null, workerId, "", null, null, "");
        model.addAttribute("category", categoryDTO);
        WorkerEntityDTO worker = workerEntityService.getWorkerEntityById(workerId);
        model.addAttribute("worker", worker);
        return "categories-add";
    }

    @PostMapping("/workers/view/{workerId}/categories/add")
    public String addCategory(@PathVariable Long workerId,
                           @ModelAttribute("category") @Valid CategoryDTO categoryDTO) {

        CategoryDTO dtoToSave = new CategoryDTO(
                null,
                workerId,
                categoryDTO.title(),
                categoryDTO.start(),
                categoryDTO.end(),
                categoryDTO.description()
        );

        categoryService.createCategory(dtoToSave);
        return "redirect:/workers/view/" + workerId + "/categories";
    }


    // Форма редактирования категории
    @GetMapping("/workers/view/{workerId}/categories/{categoryId}/edit")
    public String showEditForm(@PathVariable Long workerId,
                               @PathVariable Long categoryId,
                               Model model) {

        CategoryDTO category = categoryService.getCategoryById(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("workerId", workerId);
        return "categories-edit";
    }

    @PostMapping("/workers/view/{workerId}/categories/{categoryId}/edit")
    public String editCategory(@PathVariable Long workerId,
                               @PathVariable Long categoryId,
                               @ModelAttribute("category") @Valid CategoryDTO categoryDTO) {

        CategoryDTO dtoToUpdate = new CategoryDTO(
                categoryId,
                workerId,
                categoryDTO.title(),
                categoryDTO.start(),
                categoryDTO.end(),
                categoryDTO.description()
        );

        categoryService.updateCategory(categoryId, dtoToUpdate);
        return "redirect:/workers/view/" + workerId + "/categories";
    }


    // Удаление стажировки
    @PostMapping("/workers/view/{workerId}/categories/{categoryId}/delete")
    public String deleteCategory(@PathVariable Long workerId,
                              @PathVariable Long categoryId) {

        categoryService.deleteCategory(categoryId);
        return "redirect:/workers/view/" + workerId + "/categories";
    }

    @PostMapping("/workers/view/{workerId}/categories/{categoryId}/save")
    public String saveEditedCategory(
            @PathVariable Long workerId,
            @PathVariable Long categoryId,
            @ModelAttribute("category") @Valid CategoryDTO categoryDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryDTO);
            return "categories"; // возвращаем шаблон редактирования
        }

        categoryService.updateCategory(categoryId, categoryDTO);

        return "redirect:/workers/view/" + workerId + "/categories";
    }

}
