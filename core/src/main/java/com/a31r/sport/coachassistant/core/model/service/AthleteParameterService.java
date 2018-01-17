package com.a31r.sport.coachassistant.core.model.service;

import com.a31r.sport.coachassistant.core.model.AthleteParameter;
import com.a31r.sport.coachassistant.core.model.repository.AthleteParameterRepository;
import com.a31r.sport.coachassistant.core.model.repository.AthleteParameterValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class AthleteParameterService extends AbstractDataService<AthleteParameter> {

    @Autowired
    private AthleteParameterRepository repository;
    @Autowired
    private AthleteParameterValueRepository athleteParameterValueRepository;

    @Override
    JpaRepository<AthleteParameter, Long> getRepository() {
        return repository;
    }

    @Override
    public void includeMembers(AthleteParameter object) {
        object.setValues(athleteParameterValueRepository.findAllByParameter(object));
    }
}