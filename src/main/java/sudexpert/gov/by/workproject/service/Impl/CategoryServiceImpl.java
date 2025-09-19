package sudexpert.gov.by.workproject.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import sudexpert.gov.by.workproject.dto.CategoryDTO;
import sudexpert.gov.by.workproject.error.ErrorMessages;
import sudexpert.gov.by.workproject.exception.ResourceNotFoundException;
import sudexpert.gov.by.workproject.mapper.CategoryMapper;
import sudexpert.gov.by.workproject.mapper.WorkerEntityMapper;
import sudexpert.gov.by.workproject.model.Category;
import sudexpert.gov.by.workproject.repository.CategoryRepository;
import sudexpert.gov.by.workproject.service.CategoryService;
import sudexpert.gov.by.workproject.service.WorkerEntityService;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    CategoryMapper categoryMapper;
    CategoryRepository categoryRepository;
    WorkerEntityService workerEntityService;
    WorkerEntityMapper workerEntityMapper;


    @Override
    public CategoryDTO createCategory(CategoryDTO createCategoryDTO) {
        Category category = categoryMapper.toEntity(createCategoryDTO);
        category.setWorker(workerEntityMapper.toEntity(workerEntityService.getWorkerEntityById(createCategoryDTO.workerId())));
        //train.setWorkerId(trainDTO.workerId());
        return categoryMapper.toDTO(categoryRepository.save(category));
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
    public List<CategoryDTO> getCategoriesByWorkerId(Long workerId) {
        List<Category> categories = categoryRepository.findCategoriesByWorkerId(workerId);
        return categories.stream()
                .map(categoryMapper::toDTO).toList();
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = findCategoryByIdOrThrow(id);
        return categoryMapper.toDTO(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    public CategoryDTO getLastCategoryForWorker(Long workerId) {
        return categoryMapper.toDTO(categoryRepository.findCategoriesByWorkerId(workerId).stream()
                .max(Comparator.comparing(Category::getEnd))
                .orElse(null));

    }

    //---------------------------------------------------------------------------------------------------------
    private Category findCategoryByIdOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_ID_MESSAGE, "Category", id)));

    }


}
