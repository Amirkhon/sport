package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.AthleteParameterType;
import com.a31r.sport.coachassistant.core.model.repository.AthleteParameterTypeRepository;
import com.a31r.sport.coachassistant.core.service.AthleteParameterTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class AthleteParameterTypeServiceImpl extends AbstractDataService<AthleteParameterType> implements AthleteParameterTypeService {

    @Autowired
    private AthleteParameterTypeRepository repository;

    @Override
    JpaRepository<AthleteParameterType, Long> getRepository() {
        return repository;
    }

}
