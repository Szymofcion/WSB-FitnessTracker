package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @PostMapping
    public ResponseEntity<TrainingDto> addTraining(@RequestBody TrainingDto trainingDto) {
        Training addedTraining = trainingService.createTraining(trainingMapper.toEntity(trainingDto));
        TrainingDto addedTrainingDto = trainingMapper.toDto(addedTraining);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTrainingDto);
    }

    @GetMapping("/{trainingId}")
    public ResponseEntity<TrainingDto> getTrainingById(@PathVariable Long trainingId) {
        return trainingService.getTraining(trainingId)
                .map(trainingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDto trainingDto) {
        Training updatedTraining = trainingService.updateTraining(trainingId, trainingMapper.toEntity(trainingDto));
        TrainingDto updatedTrainingDto = trainingMapper.toDto(updatedTraining);
        return ResponseEntity.ok(updatedTrainingDto);
    }

    @DeleteMapping("/{trainingId}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long trainingId) {
        trainingService.deleteTraining(trainingId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/finished/{finishDate}")
    public List<TrainingDto> getAllFinishedTrainings(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate) {
        return trainingService.findAllFinishedTrainings(finishDate)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }
    @GetMapping("/byActivity")
    public List<TrainingDto> getAllTrainingsByActivity(@RequestParam("type") String activityType) {
        // Pobierz listę treningów dla określonego typu aktywności
        List<Training> trainings = trainingService.findAllTrainingsByActivity(activityType);
        // Mapuj treningi na DTO i zwróć listę DTO
        return trainings.stream()
                .map(trainingMapper::toDto)
                .toList();
    }

}
