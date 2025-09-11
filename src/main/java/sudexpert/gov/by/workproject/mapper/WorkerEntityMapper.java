package sudexpert.gov.by.workproject.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.model.WorkerEntity;

@Mapper(
        componentModel = "spring",
        uses = {
                CategoryMapper.class,
                ECCMapper.class,
                QualificationMapper.class,
                TrainMapper.class,
                VacationMapper.class
        }
)
public interface WorkerEntityMapper {

    // ✅ MapStruct сам вызовет categoryMapper.toDTO, trainMapper.toDTO и т.д.
    WorkerEntityDTO toDTO(WorkerEntity workerEntity);

    // при создании - коллекции игнорируем
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "trains", ignore = true)
    @Mapping(target = "eccs", ignore = true)
    @Mapping(target = "qualifications", ignore = true)
    @Mapping(target = "vacations", ignore = true)
    WorkerEntity toEntity(WorkerEntityDTO workerEntityDTO);

    // при обновлении - тоже игнорируем
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "trains", ignore = true)
    @Mapping(target = "eccs", ignore = true)
    @Mapping(target = "qualifications", ignore = true)
    @Mapping(target = "vacations", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateWorkerEntityFromRequest(
            WorkerEntityDTO workerEntityDTO,
            @MappingTarget WorkerEntity workerEntity
    );

    // привязка "обратно" к воркеру
    @AfterMapping
    default void linkCollections(@MappingTarget WorkerEntity worker) {
        if (worker.getCategories() != null) {
            worker.getCategories().forEach(c -> c.setWorker(worker));
        }
        if (worker.getEccs() != null) {
            worker.getEccs().forEach(c -> c.setWorker(worker));
        }
        if (worker.getTrains() != null) {
            worker.getTrains().forEach(c -> c.setWorker(worker));
        }
        if (worker.getVacations() != null) {
            worker.getVacations().forEach(c -> c.setWorker(worker));
        }
        if (worker.getQualifications() != null) {
            worker.getQualifications().forEach(c -> c.setWorker(worker));
        }
    }
}
