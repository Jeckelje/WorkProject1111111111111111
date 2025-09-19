package sudexpert.gov.by.workproject.service;

import sudexpert.gov.by.workproject.dto.CategoryDTO;

import java.util.List;


public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    List<CategoryDTO> getCategoriesByWorkerId(Long workerId);

    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> getAllCategories();

    CategoryDTO getLastCategoryForWorker(Long workerId);

}
