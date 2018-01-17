package com.a31r.sport.coachassistant.core.model.service;

import com.a31r.sport.coachassistant.core.model.TrainingExercise;
import com.a31r.sport.coachassistant.core.model.repository.TrainingExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class TrainingExerciseService extends AbstractDataService<TrainingExercise> {

    @Autowired
    private TrainingExerciseRepository repository;

    @Override
    JpaRepository<TrainingExercise, Long> getRepository() {
        return repository;
    }

}