package com.a31r.sport.coachassistant.core.model.service;

import com.a31r.sport.coachassistant.core.model.Coach;
import com.a31r.sport.coachassistant.core.model.repository.CoachRepository;
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
public class CoachService extends AbstractDataService<Coach> {

    @Autowired
    private CoachRepository repository;
    @Autowired
    private TrainingSessionRepository trainingSessionRepository;
    @Autowired
    private TrainingGroupRepository trainingGroupRepository;

    @Override
    JpaRepository<Coach, Long> getRepository() {
        return repository;
    }

    @Override
    public void includeMembers(Coach object) {
        object.setTrainingGroups(new HashSet<>(trainingGroupRepository.findAllByCoach(object)));
        object.setTrainingSessions(new HashSet<>(trainingSessionRepository.findAllByCoach(object)));
    }
}