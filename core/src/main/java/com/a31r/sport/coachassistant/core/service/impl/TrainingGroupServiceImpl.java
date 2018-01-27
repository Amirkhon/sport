package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.TrainingGroup;
import com.a31r.sport.coachassistant.core.model.repository.TrainingGroupRepository;
import com.a31r.sport.coachassistant.core.service.TrainingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class TrainingGroupServiceImpl extends AbstractDataService<TrainingGroup> implements TrainingGroupService {

    @Autowired
    private TrainingGroupRepository repository;

    @Override
    JpaRepository<TrainingGroup, Long> getRepository() {
        return repository;
    }

    @Override
    public TrainingGroup initialize(TrainingGroup object) {
        TrainingGroup trainingGroup = repository.getOne(object.getId());
        if (trainingGroup != null) {
            trainingGroup.getMembers().size();
            trainingGroup.getSessions().size();
        }
        return object;
    }
}