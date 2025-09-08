package sudexpert.gov.by.workproject.mapper;
import org.mapstruct.*;
import sudexpert.gov.by.workproject.dto.CategoryDTO;
import sudexpert.gov.by.workproject.dto.request.create.CreateCategoryDTO;
import sudexpert.gov.by.workproject.dto.response.CategoryResponse;
import sudexpert.gov.by.workproject.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {


    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);

}
