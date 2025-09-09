package sudexpert.gov.by.workproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import sudexpert.gov.by.workproject.dto.CategoryDTO;
import sudexpert.gov.by.workproject.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {


    CategoryDTO toDTO(Category category);

    Category toEntity(CategoryDTO categoryDTO);

    @Mapping(target = "id", ignore = true)
    void updateCategoryFromRequest(CategoryDTO categoryDTO, @MappingTarget Category category);
}
