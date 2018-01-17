package com.a31r.sport.coachassistant.core.model.repository;

import com.a31r.sport.coachassistant.core.model.Athlete;
import com.a31r.sport.coachassistant.core.model.TrainingGroup;
import com.a31r.sport.coachassistant.core.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by bahodurova on 1/7/2018.
 */
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    List<Athlete> findAllByGroupsContains(UserGroup group);
}
