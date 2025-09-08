package sudexpert.gov.by.workproject.service;

import org.hibernate.sql.Update;
import sudexpert.gov.by.workproject.dto.CategoryDTO;
import sudexpert.gov.by.workproject.dto.request.create.CreateCategoryDTO;
import sudexpert.gov.by.workproject.dto.response.CategoryResponse;

import java.util.List;


public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory (Long id,CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(CategoryDTO categoryDTO);

    CategoryDTO getByUserId(Long userId);

    CategoryDTO getById(Long id);

    List<CategoryDTO> getAllCategories();

}
