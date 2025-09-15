package sudexpert.gov.by.workproject.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sudexpert.gov.by.workproject.dto.AchievementDTO;
import sudexpert.gov.by.workproject.dto.TrainDTO;
import sudexpert.gov.by.workproject.dto.validation.OnCreate;
import sudexpert.gov.by.workproject.dto.validation.OnUpdate;
import sudexpert.gov.by.workproject.service.AchievementService;

import java.util.List;

@RestController
@RequestMapping("api/achievement")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AchievementController {

    AchievementService achievementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AchievementDTO createAchievement(@RequestBody @Validated(OnCreate.class) AchievementDTO achievementDTO) {
        return achievementService.createAchievement(achievementDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AchievementDTO updateAchievement(
            @PathVariable Long id,
            @RequestBody @Validated(OnUpdate.class) AchievementDTO achievementDTO) {
        return achievementService.updateAchievement(id, achievementDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAchievement(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AchievementDTO getAchievementById(@PathVariable Long id) {
        return achievementService.getAchievementById(id);
    }

    @GetMapping("/worker/{workerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AchievementDTO> getAchievementsByWorkerId(@PathVariable Long workerId) {
        return achievementService.getAchievementsByWorkerId(workerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AchievementDTO> getAllAchievements() {
        return achievementService.getAllAchievements();
    }
}
