package com.a31r.sport.coachassistant.core.model.service;

import com.a31r.sport.coachassistant.core.model.TrainingGroup;
import com.a31r.sport.coachassistant.core.model.repository.AthleteRepository;
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
public class TrainingGroupService extends AbstractDataService<TrainingGroup> {

    @Autowired
    private TrainingGroupRepository repository;
    @Autowired
    private TrainingSessionRepository trainingSessionRepository;
    @Autowired
    private AthleteRepository athleteRepository;

    @Override
    JpaRepository<TrainingGroup, Long> getRepository() {
        return repository;
    }

    @Override
    public void includeMembers(TrainingGroup object) {
        object.setSessions(new HashSet<>(trainingSessionRepository.findAllByGroupsContains(object)));
        object.setMembers(new HashSet<>(athleteRepository.findAllByGroupsContains(object)));
    }
}