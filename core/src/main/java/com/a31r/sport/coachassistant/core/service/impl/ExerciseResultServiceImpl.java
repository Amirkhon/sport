package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.ExerciseResult;
import com.a31r.sport.coachassistant.core.model.repository.ExerciseResultRepository;
import com.a31r.sport.coachassistant.core.service.ExerciseResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class ExerciseResultServiceImpl extends AbstractDataService<ExerciseResult> implements ExerciseResultService {

    @Autowired
    private ExerciseResultRepository repository;

    @Override
    JpaRepository<ExerciseResult, Long> getRepository() {
        return repository;
    }

}
