package com.a31r.sport.coachassistant.core.model.service;

import com.a31r.sport.coachassistant.core.model.ExerciseResult;
import com.a31r.sport.coachassistant.core.model.repository.ExerciseResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class ExerciseResultService extends AbstractDataService<ExerciseResult> {

    @Autowired
    private ExerciseResultRepository repository;

    @Override
    JpaRepository<ExerciseResult, Long> getRepository() {
        return repository;
    }

}
