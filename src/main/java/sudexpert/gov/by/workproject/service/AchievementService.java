package sudexpert.gov.by.workproject.service;

import sudexpert.gov.by.workproject.dto.AchievementDTO;
import sudexpert.gov.by.workproject.dto.CategoryDTO;

import java.util.List;

public interface AchievementService {

    AchievementDTO createAchievement(AchievementDTO achievementDTO);

    AchievementDTO updateAchievement(Long id, AchievementDTO achievementDTO);

    void deleteAchievement(Long id);

    List<AchievementDTO> getAchievementsByWorkerId(Long workerId);

    AchievementDTO getAchievementById(Long id);

    List<AchievementDTO> getAllAchievements();

}
