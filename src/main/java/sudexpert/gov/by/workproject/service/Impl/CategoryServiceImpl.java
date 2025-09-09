package sudexpert.gov.by.workproject.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sudexpert.gov.by.workproject.dto.CategoryDTO;
import sudexpert.gov.by.workproject.error.ErrorMessages;
import sudexpert.gov.by.workproject.mapper.CategoryMapper;
import sudexpert.gov.by.workproject.exception.ResourceNotFoundException;
import sudexpert.gov.by.workproject.model.Category;
import sudexpert.gov.by.workproject.repository.CategoryRepository;
import sudexpert.gov.by.workproject.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;


    @Override
    public CategoryDTO createCategory(CategoryDTO createCategoryDTO) {
        return categoryMapper.toDTO(categoryRepository.save(categoryMapper.toEntity(createCategoryDTO)));
    }


    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = findCategoryByIdOrThrow(id);
        categoryMapper.updateCategoryFromRequest(categoryDTO, existingCategory);

        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = findCategoryByIdOrThrow(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDTO getByWorkerId(Long workerId) {
        Category category = findCategoryByWorkerIdOrThrow(workerId);
        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO getById(Long id) {
        return null;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    //---------------------------------------------------------------------------------------------------------
    private Category findCategoryByIdOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_ID_MESSAGE, "Category", id)));

    }

    private Category findCategoryByWorkerIdOrThrow(Long workerId) {
        Category category =categoryRepository.findCategoryByWorkerId(workerId);
        if(category == null){
            throw new ResourceNotFoundException(
                    String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_CUSTOMER_NAME_MESSAGE, "Category for worker", workerId)
            );
        }
        return category;
    }
}
