package sudexpert.gov.by.workproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import sudexpert.gov.by.workproject.dto.AchievementDTO;
import sudexpert.gov.by.workproject.dto.CategoryDTO;
import sudexpert.gov.by.workproject.model.Achievement;
import sudexpert.gov.by.workproject.model.Category;

@Mapper(componentModel = "spring")
public interface AchievementMapper {

    AchievementDTO toDTO(Achievement achievement);

    Achievement toEntity(AchievementDTO achievementDTO);

    @Mapping(target = "id", ignore = true)
    void updateAchievementFromRequest(AchievementDTO achievementDTO, @MappingTarget Achievement achievement);

}
