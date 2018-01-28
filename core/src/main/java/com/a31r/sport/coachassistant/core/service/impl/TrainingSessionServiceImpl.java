package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.TrainingSession;
import com.a31r.sport.coachassistant.core.model.repository.TrainingSessionRepository;
import com.a31r.sport.coachassistant.core.service.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    @Transactional
    @Override
    public TrainingSession initialize(TrainingSession object) {
        return initialize(object, true);
    }

    @Transactional
    public TrainingSession initialize(TrainingSession object, boolean includeResults) {
        object = repository.getOne(object.getId());
        if (object != null) {
            object.getGroups().size();
            object.getExercises().size();
            object.getCoaches().size();

            if (includeResults) {
                object.getResults().size();
            }
        }
        return object;
    }
}
