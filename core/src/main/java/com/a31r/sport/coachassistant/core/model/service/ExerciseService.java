package com.a31r.sport.coachassistant.core.model.service;

import com.a31r.sport.coachassistant.core.model.Exercise;
import com.a31r.sport.coachassistant.core.model.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class ExerciseService extends AbstractDataService<Exercise> {

    @Autowired
    private ExerciseRepository repository;

    @Override
    JpaRepository<Exercise, Long> getRepository() {
        return repository;
    }
}