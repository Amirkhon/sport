package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.TrainingExerciseGroup;
import com.a31r.sport.coachassistant.core.model.repository.TrainingExerciseGroupRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingExerciseRepository;
import com.a31r.sport.coachassistant.core.service.TrainingExerciseGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class TrainingExerciseGroupServiceImpl extends AbstractDataService<TrainingExerciseGroup> implements TrainingExerciseGroupService {

    @Autowired
    private TrainingExerciseGroupRepository repository;
    @Autowired
    private TrainingExerciseRepository trainingExerciseRepository;

    @Override
    JpaRepository<TrainingExerciseGroup, Long> getRepository() {
        return repository;
    }

    @Override
    public TrainingExerciseGroup initialize(TrainingExerciseGroup object) {
        object.setMembers(new HashSet<>(trainingExerciseRepository.findAllByGroupsContains(object)));
        return object;
    }
}
