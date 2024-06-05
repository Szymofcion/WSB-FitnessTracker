package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingProvider {
    private final TrainingRepository trainingRepository;

    @Override
    public List<Training> findTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId);
    }
    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public Training createTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public Optional<Training> getTraining(Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    // Implementacje pozostałych metod z interfejsu TrainingProvider

    @Override
    public List<Training> getAllTrainings() {
        return null;
    }

    @Override
    public List<Training> getAllTrainingsForUser(long userId) {
        return null;
    }

    @Override
    public Training addTraining(Training training) {
        return null;
    }

    @Override
    public List<Training> getAllFinishTrainings(Date finishData) {
        return null;
    }

    @Override
    public List<Training> getAllTrainingTypes(ActivityType activity) {
        return null;
    }

    @Override
    public Training updateTraining(Long trainingId, Training trainingDetails) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));

        training.setStartTime(trainingDetails.getStartTime());
        training.setEndTime(trainingDetails.getEndTime());
        training.setActivityType(trainingDetails.getActivityType());
        training.setDistance(trainingDetails.getDistance());
        training.setAverageSpeed(trainingDetails.getAverageSpeed());

        return trainingRepository.save(training);
    }

    @Override
    public void deleteTraining(Long trainingId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));

        trainingRepository.delete(training);
    }
}
