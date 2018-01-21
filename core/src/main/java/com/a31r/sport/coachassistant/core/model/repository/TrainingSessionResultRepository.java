package com.a31r.sport.coachassistant.core.model.repository;

import com.a31r.sport.coachassistant.core.model.TrainingSession;
import com.a31r.sport.coachassistant.core.model.TrainingSessionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by bahodurova on 1/21/2018.
 */
public interface TrainingSessionResultRepository extends JpaRepository<TrainingSessionResult, Long> {
    List<TrainingSessionResult> findAllByTrainingSession(TrainingSession trainingSession);
    List<TrainingSessionResult> findAllByDateBetween(LocalDate start, LocalDate end);
}
