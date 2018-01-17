package com.a31r.sport.coachassistant.core.model.repository;

import com.a31r.sport.coachassistant.core.model.TrainingExercise;
import com.a31r.sport.coachassistant.core.model.TrainingExerciseGroup;
import com.a31r.sport.coachassistant.core.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by bahodurova on 1/9/2018.
 */
public interface TrainingExerciseRepository extends JpaRepository<TrainingExercise, Long> {
    List<TrainingExercise> findAllByTrainingSession(TrainingSession trainingSession);
    List<TrainingExercise> findAllByGroupsContains(TrainingExerciseGroup group);
}
