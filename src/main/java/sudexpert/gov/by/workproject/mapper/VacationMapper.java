package sudexpert.gov.by.workproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import sudexpert.gov.by.workproject.dto.VacationDTO;
import sudexpert.gov.by.workproject.model.Vacation;

@Mapper(componentModel = "spring")
public interface VacationMapper {


    VacationDTO toDTO(Vacation vacation);

    Vacation toEntity(VacationDTO vacationDTO);

    @Mapping(target = "id", ignore = true)
    void updateVacationFromRequest(VacationDTO vacationDTO, @MappingTarget Vacation vacation);
}
