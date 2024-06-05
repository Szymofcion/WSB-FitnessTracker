package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserProvider userProvider;
    private final TrainingMapper trainingMapper;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository, UserProvider userProvider, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.userProvider = userProvider;
        this.trainingMapper = trainingMapper;
    }


    public TrainingDto createTraining(Long userId, LocalDateTime startTime, LocalDateTime endTime, ActivityType activityType, double distance, double averageSpeed) {
        // Pobieramy użytkownika
        User user = userProvider.getUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Konwertujemy LocalDateTime na Date
        Date startDateTime = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
        Date endDateTime = Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());

        // Tworzymy nowy obiekt Training
        Training training = new Training(
                user,
                startDateTime,
                endDateTime,
                activityType,
                distance,
                averageSpeed
        );

        // Zapisujemy nowy trening w repozytorium
        Training savedTraining = trainingRepository.save(training);

        // Mapujemy zapisany trening na obiekt DTO i zwracamy
        return trainingMapper.toDto(savedTraining);
    }

    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }
    public List<Training> findAllTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }
    public Training updateTraining(Long trainingId, TrainingDto trainingDetailsDto) {
        // Pobierz trening z repozytorium
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));

        // Aktualizuj pola treningu na podstawie dostarczonych informacji
        Double distance = trainingDetailsDto.getDistance();
        if (distance != null) {
            training.setDistance(distance);
        }

        // Zapisz zaktualizowany trening w repozytorium i zwróć
        return trainingRepository.save(training);
    }
}
