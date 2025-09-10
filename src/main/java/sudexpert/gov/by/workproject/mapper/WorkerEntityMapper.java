package sudexpert.gov.by.workproject.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import sudexpert.gov.by.workproject.dto.WorkerEntityDTO;
import sudexpert.gov.by.workproject.model.WorkerEntity;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class,ECCMapper.class,QualificationMapper.class,TrainMapper.class,VacationMapper.class},imports = {java.util.stream.Collectors.class})
public interface WorkerEntityMapper {


    @Mapping(target = "categories", expression = "java(workerEntity.getCategories() == null ? Set.of() : workerEntity.getCategories().stream().map(categoryMapper::toDTO).collect(Collectors.toSet()))")
    @Mapping(target = "trains", expression = "java(workerEntity.getTrains() == null ? Set.of() : workerEntity.getTrains().stream().map(trainMapper::toDTO).collect(Collectors.toSet()))")
    @Mapping(target = "eccs", expression = "java(workerEntity.getEccs() == null ? Set.of() : workerEntity.getEccs().stream().map(eCCMapper::toDTO).collect(Collectors.toSet()))")
    @Mapping(target = "qualifications", expression = "java(workerEntity.getQualifications() == null ? Set.of() : workerEntity.getQualifications().stream().map(qualificationMapper::toDTO).collect(Collectors.toSet()))")
    @Mapping(target = "vacations", expression = "java(workerEntity.getVacations() == null ? Set.of() : workerEntity.getVacations().stream().map(vacationMapper::toDTO).collect(Collectors.toSet()))")
    WorkerEntityDTO toDTO(WorkerEntity workerEntity);

    WorkerEntity toEntity(WorkerEntityDTO workerEntityDTO);

    @Mapping(target = "id", ignore = true)

    void updateWorkerEntityFromRequest(WorkerEntityDTO workerEntityDTO, @MappingTarget WorkerEntity workerEntity);

    static <T> Set<T> safeCopy(Set<T> set) {
        return set == null ? Set.of() : new HashSet<>(set); // безопасная копия
    }

    @AfterMapping
    default void linkCategories(@MappingTarget WorkerEntity worker) {
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
}}
