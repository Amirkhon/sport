package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.TrainingSession;
import com.a31r.sport.coachassistant.core.model.repository.TrainingSessionRepository;
import com.a31r.sport.coachassistant.core.service.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class TrainingSessionServiceImpl extends AbstractDataService<TrainingSession> implements TrainingSessionService {

    @Autowired
    private TrainingSessionRepository repository;

    @Override
    JpaRepository<TrainingSession, Long> getRepository() {
        return repository;
    }

    @Override
    public TrainingSession initialize(TrainingSession object) {
        return initialize(object, true);
    }

    public TrainingSession initialize(TrainingSession object, boolean includeResults) {
        TrainingSession trainingSession = repository.getOne(object.getId());
        if (trainingSession != null) {
            trainingSession.getGroups().size();
            trainingSession.getExercises().size();
            if (includeResults) {
                trainingSession.getResults().size();
            }
            return trainingSession;
        }
        return object;
    }
}
