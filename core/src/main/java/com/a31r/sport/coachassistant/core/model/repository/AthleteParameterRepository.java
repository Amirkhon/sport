package com.a31r.sport.coachassistant.core.model.repository;

import com.a31r.sport.coachassistant.core.model.Athlete;
import com.a31r.sport.coachassistant.core.model.AthleteParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by bahodurova on 1/7/2018.
 */
public interface AthleteParameterRepository extends JpaRepository<AthleteParameter, Long> {
    List<AthleteParameter> findAllByAthlete(Athlete athlete);
}
