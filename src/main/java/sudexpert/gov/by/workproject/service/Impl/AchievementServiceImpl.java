package sudexpert.gov.by.workproject.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import sudexpert.gov.by.workproject.dto.AchievementDTO;
import sudexpert.gov.by.workproject.error.ErrorMessages;
import sudexpert.gov.by.workproject.exception.ResourceNotFoundException;
import sudexpert.gov.by.workproject.mapper.AchievementMapper;
import sudexpert.gov.by.workproject.mapper.WorkerEntityMapper;
import sudexpert.gov.by.workproject.model.Achievement;
import sudexpert.gov.by.workproject.repository.AchievementRepository;
import sudexpert.gov.by.workproject.service.AchievementService;
import sudexpert.gov.by.workproject.service.WorkerEntityService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AchievementServiceImpl implements AchievementService {

    AchievementRepository achievementRepository;
    AchievementMapper achievementMapper;
    WorkerEntityService workerEntityService;
    WorkerEntityMapper workerEntityMapper;


    @Override
    public AchievementDTO createAchievement(AchievementDTO achievementDTO) {
        Achievement achievement = achievementMapper.toEntity(achievementDTO);
        achievement.setWorker(workerEntityMapper.toEntity(workerEntityService.getWorkerEntityById(achievementDTO.workerId())));
        //train.setWorkerId(trainDTO.workerId());
        return achievementMapper.toDTO(achievementRepository.save(achievement));
    }

    @Override
    public AchievementDTO updateAchievement(Long id, AchievementDTO achievementDTO) {
        Achievement existingAchievement = findAchievementByIdOrThrow(id);
        achievementMapper.updateAchievementFromRequest(achievementDTO, existingAchievement);

        Achievement updatedAchievement = achievementRepository.save(existingAchievement);
        return achievementMapper.toDTO(updatedAchievement);
    }

    @Override
    public void deleteAchievement(Long id) {
        findAchievementByIdOrThrow(id);
        achievementRepository.deleteById(id);
    }

    @Override
    public List<AchievementDTO> getAchievementsByWorkerId(Long workerId) {
        List<Achievement> achievements = findAchievementsByWorkerIdOrThrow(workerId);
        return achievements.stream().map(achievementMapper::toDTO).toList();
    }

    @Override
    public AchievementDTO getAchievementById(Long id) {
        Achievement achievement = findAchievementByIdOrThrow(id);
        return achievementMapper.toDTO(achievement);
    }

    @Override
    public List<AchievementDTO> getAllAchievements() {
        return achievementRepository.findAll()
                .stream().map(achievementMapper::toDTO)
                .toList();
    }

    //---------------------------------------------------------------------------------------------------------
    private Achievement findAchievementByIdOrThrow(Long id) {
        return achievementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_ID_MESSAGE, "Achievement", id)));
    }

    private List<Achievement> findAchievementsByWorkerIdOrThrow(Long workerId) {
        List<Achievement> achievements = achievementRepository.findAchievementsByWorkerId(workerId);
        if (achievements.isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND_BY_CUSTOMER_NAME_MESSAGE, "Achievements for worker", workerId));
        }
        return achievements;
    }

}
