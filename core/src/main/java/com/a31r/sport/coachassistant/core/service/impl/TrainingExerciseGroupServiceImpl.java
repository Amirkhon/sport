package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.TrainingExerciseGroup;
import com.a31r.sport.coachassistant.core.model.repository.TrainingExerciseGroupRepository;
import com.a31r.sport.coachassistant.core.service.TrainingExerciseGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class TrainingExerciseGroupServiceImpl extends AbstractDataService<TrainingExerciseGroup> implements TrainingExerciseGroupService {

    @Autowired
    private TrainingExerciseGroupRepository repository;

    @Override
    JpaRepository<TrainingExerciseGroup, Long> getRepository() {
        return repository;
    }

    @Transactional
    @Override
    public TrainingExerciseGroup initialize(TrainingExerciseGroup object) {
        object = repository.getOne(object.getId());
        if (object != null) {
            object.getMembers().size();
        }
        return object;
    }
}
