package sudexpert.gov.by.workproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import sudexpert.gov.by.workproject.dto.QualificationDTO;
import sudexpert.gov.by.workproject.model.Qualification;

@Mapper(componentModel = "spring")
public interface QualificationMapper {


    QualificationDTO toDTO(Qualification qualification);

    Qualification toEntity(QualificationDTO qualificationDTO);

    @Mapping(target = "id", ignore = true)
    void updateQualificationFromRequest(QualificationDTO qualificationDTO, @MappingTarget Qualification qualification);
}
