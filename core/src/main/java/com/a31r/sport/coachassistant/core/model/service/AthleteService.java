package com.a31r.sport.coachassistant.core.model.service;

import com.a31r.sport.coachassistant.core.model.Athlete;
import com.a31r.sport.coachassistant.core.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class AthleteService extends AbstractDataService<Athlete> {

    @Autowired
    private AthleteRepository repository;
    @Autowired
    private AthleteParameterRepository athleteParameterRepository;
    @Autowired
    private ExerciseResultRepository exerciseResultRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private UserPropertyRepository userPropertyRepository;

    @Override
    JpaRepository<Athlete, Long> getRepository() {
        return repository;
    }

    @Override
    public void includeMembers(Athlete object) {
        object.setProperties(userPropertyRepository.findAllByUser(object));
        object.setParameters(athleteParameterRepository.findAllByAthlete(object));
        object.setResults(exerciseResultRepository.findAllByAthlete(object));
        object.setGroups(new HashSet<>(userGroupRepository.findAllByMembersContains(object)));
    }
}