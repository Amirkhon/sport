package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.TrainingSessionResult;
import com.a31r.sport.coachassistant.core.model.repository.TrainingSessionResultRepository;
import com.a31r.sport.coachassistant.core.service.TrainingSessionResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/21/2018.
 */
@Service
public class TrainingSessionResultServiceImpl extends AbstractDataService<TrainingSessionResult> implements TrainingSessionResultService {

    @Autowired
    private TrainingSessionResultRepository repository;

    @Override
    JpaRepository<TrainingSessionResult, Long> getRepository() {
        return repository;
    }

    @Override
    public TrainingSessionResult initialize(TrainingSessionResult object) {
        TrainingSessionResult sessionResult = repository.getOne(object.getId());
        if(sessionResult != null) {
            sessionResult.getResults().size();
            sessionResult.getAttendances().size();
            object = sessionResult;
        }
        return object;
    }

    @Override
    public void delete(TrainingSessionResult object) {

        super.delete(object);
    }
}
