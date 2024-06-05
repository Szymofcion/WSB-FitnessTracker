package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;

import java.util.List;

public interface TrainingProvider {
    List<Training> findTrainingsByUserId(Long userId);
}
