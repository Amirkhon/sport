package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.Athlete;
import com.a31r.sport.coachassistant.core.model.UserGroup;
import com.a31r.sport.coachassistant.core.model.repository.*;
import com.a31r.sport.coachassistant.core.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class AthleteServiceImpl extends AbstractDataService<Athlete> implements AthleteService {

    @Autowired
    private AthleteRepository repository;
    @Autowired
    private UserGroupRepository userGroupRepository;

    @Override
    JpaRepository<Athlete, Long> getRepository() {
        return repository;
    }

    @Override
    public Athlete initialize(Athlete object) {
        return initialize(object, true, true);
    }

    @Transactional
    public Athlete initialize(Athlete object, boolean includeResults, boolean includeAttendances) {
        Athlete athlete = repository.getOne(object.getId());
        if (athlete != null) {
            athlete.getProperties().size();
            athlete.getGroups().size();
            athlete.getParameters().size();

            if (includeResults) {
                athlete.getResults().size();
            }

            if (includeAttendances) {
                athlete.getAttendances().size();
            }
        }

        return object;
    }

    @Transactional
    @Override
    public void delete(Athlete object) {
        Athlete athlete = repository.getOne(object.getId());
        if (athlete != null) {
            for (UserGroup group : object.getGroups()) {
                group.removeMember(object);
                userGroupRepository.save(group);
            }
            object = athlete;
        }

        super.delete(object);
    }
}