package sudexpert.gov.by.workproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.model.WorkerEntity;

@Mapper(componentModel = "spring")
public interface WorkerEntityMapper {


    WorkerEntityDTO toDTO(WorkerEntity workerEntity);

    WorkerEntity toEntity(WorkerEntityDTO workerEntityDTO);

    @Mapping(target = "id", ignore = true)
    void updateWorkerEntityFromRequest(WorkerEntityDTO workerEntityDTO, @MappingTarget WorkerEntity workerEntity);

    @Mapping(target = "id", ignore = true)
    void updateWorkerEntityFromRequest(WorkerEntity workerEntityNew, @MappingTarget WorkerEntity workerEntity);
}
