package com.a31r.sport.coachassistant.core.model.repository;

import com.a31r.sport.coachassistant.core.model.AthleteAttendance;
import com.a31r.sport.coachassistant.core.model.TrainingSessionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by bahodurova on 1/21/2018.
 */
public interface AthleteAttendanceRepository extends JpaRepository<AthleteAttendance, Long> {
    List<AthleteAttendance> findAllByTrainingSessionResult(TrainingSessionResult result);
}
