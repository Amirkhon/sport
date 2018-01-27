package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.AthleteParameter;
import com.a31r.sport.coachassistant.core.model.repository.AthleteParameterRepository;
import com.a31r.sport.coachassistant.core.service.AthleteParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class AthleteParameterServiceImpl extends AbstractDataService<AthleteParameter> implements AthleteParameterService {

    @Autowired
    private AthleteParameterRepository repository;

    @Override
    JpaRepository<AthleteParameter, Long> getRepository() {
        return repository;
    }

}