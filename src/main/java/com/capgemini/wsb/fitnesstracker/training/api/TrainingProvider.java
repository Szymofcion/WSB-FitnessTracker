package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {
    List<Training> findTrainingsByUserId(Long userId);

    List<Training> findAllTrainings();

    Training createTraining(Training training);

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<Training> getTraining(
            Long trainingId
    );
    List<Training> getAllTrainings();
    List<Training> getAllTrainingsForUser(
            long userId
    );
    Training addTraining(
            Training training
    );
    List<Training> getAllFinishTrainings(
            Date finishData
    );
    List<Training> getAllTrainingTypes(
            ActivityType activity
    );

    Training updateTraining(Long trainingId, Training trainingDetails);

    void deleteTraining(Long trainingId);

    List<Training> findAllFinishedTrainings(LocalDate finishDate);
}