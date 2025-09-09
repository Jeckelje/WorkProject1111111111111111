package sudexpert.gov.by.workproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import sudexpert.gov.by.workproject.dto.ECCDTO;
import sudexpert.gov.by.workproject.model.ECC;

@Mapper(componentModel = "spring")
public interface ECCMapper {


    ECCDTO toDTO(ECC category);

    ECC toEntity(ECCDTO eccdto);

    @Mapping(target = "id", ignore = true)
    void updateEССFromRequest(ECCDTO eccDto, @MappingTarget ECC ecc);
}
