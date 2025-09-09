package sudexpert.gov.by.workproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import sudexpert.gov.by.workproject.dto.TrainDTO;
import sudexpert.gov.by.workproject.model.Train;

@Mapper(componentModel = "spring")
public interface TrainMapper {


    TrainDTO toDTO(Train train);

    Train toEntity(TrainDTO trainDTO);

    @Mapping(target = "id", ignore = true)
    void updateTrainFromRequest(TrainDTO trainDTO, @MappingTarget Train train);
}
