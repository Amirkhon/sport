package com.a31r.sport.coachassistant.core.model.service;

import com.a31r.sport.coachassistant.core.model.TrainingSessionResult;
import com.a31r.sport.coachassistant.core.model.repository.AthleteAttendanceRepository;
import com.a31r.sport.coachassistant.core.model.repository.ExerciseResultRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingSessionResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/21/2018.
 */
@Service
public class TrainingSessionResultService extends AbstractDataService<TrainingSessionResult> {

    @Autowired
    private TrainingSessionResultRepository repository;
    @Autowired
    private AthleteAttendanceRepository athleteAttendanceRepository;
    @Autowired
    private ExerciseResultRepository exerciseResultRepository;

    @Override
    JpaRepository<TrainingSessionResult, Long> getRepository() {
        return repository;
    }

    @Override
    public void includeMembers(TrainingSessionResult object) {
        object.setAttendances(athleteAttendanceRepository.findAllByTrainingSessionResult(object));
        object.setResults(exerciseResultRepository.findAllByTrainingSessionResult(object));
    }
}
