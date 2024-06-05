package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserProvider userProvider;
    private final TrainingMapper trainingMapper;

    public TrainingService(TrainingRepository trainingRepository, UserProvider userProvider, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.userProvider = userProvider;
        this.trainingMapper = trainingMapper;
    }

    public TrainingDto createTraining(Long userId, LocalDateTime startTime, LocalDateTime endTime, ActivityType activityType, double distance, double averageSpeed) {
        // Pobieramy użytkownika
        User user = userProvider.getUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Tworzymy nowy trening
        Training training = new Training(
                user,
                startTime,
                endTime,
                activityType,
                distance,
                averageSpeed
        );

        // Zapisujemy nowy trening w repozytorium
        Training savedTraining = trainingRepository.save(training);

        // Mapujemy zapisany trening na obiekt DTO i zwracamy
        return trainingMapper.toDto(savedTraining);
    }
}
