package sudexpert.gov.by.workproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.CategoryDTO;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;
import sudexpert.gov.by.workproject.service.CategoryService;
import sudexpert.gov.by.workproject.swagger.CategoryAPI;

import java.util.List;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
@Validated
public class CategoryController implements CategoryAPI {

    private final CategoryService categoryService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public CategoryDTO createCategory(@RequestBody @Validated(OnCreate.class) CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CategoryDTO updateCategory(
            @PathVariable Long id,
            @RequestBody @Validated(OnUpdate.class) CategoryDTO categoryDTO) {
        return categoryService.updateCategory(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CategoryDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/worker/{workerId}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public CategoryDTO getCategoryByWorkerId(@PathVariable Long workerId) {
        return categoryService.getCategoryByWorkerId(workerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }


}
