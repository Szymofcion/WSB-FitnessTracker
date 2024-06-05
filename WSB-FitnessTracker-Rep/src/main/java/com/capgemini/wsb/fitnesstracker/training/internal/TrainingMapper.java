package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrainingMapper {

    private final UserProvider userProvider;

    public TrainingDto toDto(Training training) {
        if (training == null) {
            return null;
        }
        return new TrainingDto(
                training.getId(),
                training.getUser() != null ? training.getUser().getId() : null,
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    public Training toEntity(TrainingDto trainingDto) {
        if (trainingDto == null) {
            return null;
        }
        User user = null;
        if (trainingDto.getUserId() != null) {
            user = userProvider.getUser(trainingDto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
        }
        return new Training(
                user,
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed()
        );
    }

    public List<TrainingDto> toDtoList(List<Training> trainingList) {
        return trainingList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Training> toEntityList(List<TrainingDto> trainingDtoList) {
        return trainingDtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<TrainingDto> findTrainingsForUser(Long userId) {
        User user = userProvider.getUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Training> userTrainings = user.getTrainings();
        return toDtoList(userTrainings);
    }

}
