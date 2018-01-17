package com.a31r.sport.coachassistant.core.model.service;

import com.a31r.sport.coachassistant.core.model.TrainingSession;
import com.a31r.sport.coachassistant.core.model.repository.TrainingExerciseRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingGroupRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class TrainingSessionService extends AbstractDataService<TrainingSession> {

    @Autowired
    private TrainingSessionRepository repository;
    @Autowired
    private TrainingGroupRepository trainingGroupRepository;
    @Autowired
    private TrainingExerciseRepository trainingExerciseRepository;

    @Override
    JpaRepository<TrainingSession, Long> getRepository() {
        return repository;
    }

    @Override
    public void includeMembers(TrainingSession object) {
        object.setExercises(trainingExerciseRepository.findAllByTrainingSession(object));
        object.setGroups(new HashSet<>(trainingGroupRepository.findAllBySessionsContains(object)));
    }
}
