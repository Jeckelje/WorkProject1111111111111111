package sudexpert.gov.by.workproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.CategoryDTO;
import sudexpert.gov.by.workproject.dto.request.create.CreateCategoryDTO;
import sudexpert.gov.by.workproject.dto.response.CategoryResponse;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.service.CategoryService;
import sudexpert.gov.by.workproject.swagger.CategoryAPI;

import java.util.List;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
@Validated
public class CategoryController implements CategoryAPI {

    private final CategoryService categoryService;

    @PostMapping("/create-book")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public CategoryDTO createCategory(@RequestBody @Validated(OnCreate.class) CategoryDTO createCategoryDTO) {
        return categoryService.createCategory(createCategoryDTO);
    }

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }


}
