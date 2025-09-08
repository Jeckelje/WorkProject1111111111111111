package sudexpert.gov.by.workproject.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sudexpert.gov.by.workproject.dto.CategoryDTO;
import sudexpert.gov.by.workproject.mapper.CategoryMapper;
import sudexpert.gov.by.workproject.exception.ResourceNotFoundException;
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
    public CategoryDTO updateCategory(Long id,CategoryDTO categoryDTO) {
        if (categoryRepository.findById(id).isEmpty()){

            throw new ResourceNotFoundException("ojsgdifyuswgg");
        }
        return categoryMapper.toDTO((categoryRepository.save(categoryMapper.toEntity(categoryDTO))));
    }

    @Override
    public CategoryDTO deleteCategory(CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public CategoryDTO getByUserId(Long userId) {
        return null;
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
}
