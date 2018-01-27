package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.Exercise;
import com.a31r.sport.coachassistant.core.model.repository.ExerciseRepository;
import com.a31r.sport.coachassistant.core.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class ExerciseServiceImpl extends AbstractDataService<Exercise> implements ExerciseService {

    @Autowired
    private ExerciseRepository repository;

    @Override
    JpaRepository<Exercise, Long> getRepository() {
        return repository;
    }
}