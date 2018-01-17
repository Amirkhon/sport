package com.a31r.sport.coachassistant.core.model.repository;

import com.a31r.sport.coachassistant.core.model.AthleteParameter;
import com.a31r.sport.coachassistant.core.model.AthleteParameterValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
public interface AthleteParameterValueRepository extends JpaRepository<AthleteParameterValue, Long> {
    List<AthleteParameterValue> findAllByParameter(AthleteParameter parameter);
}
