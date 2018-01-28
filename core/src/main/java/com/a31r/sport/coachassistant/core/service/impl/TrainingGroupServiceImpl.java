package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.TrainingGroup;
import com.a31r.sport.coachassistant.core.model.TrainingSession;
import com.a31r.sport.coachassistant.core.model.repository.TrainingGroupRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingSessionRepository;
import com.a31r.sport.coachassistant.core.service.TrainingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class TrainingGroupServiceImpl extends AbstractDataService<TrainingGroup> implements TrainingGroupService {

    @Autowired
    private TrainingGroupRepository repository;
    @Autowired
    private TrainingSessionRepository sessionRepository;

    @Override
    JpaRepository<TrainingGroup, Long> getRepository() {
        return repository;
    }

    @Override
    public TrainingGroup initialize(TrainingGroup object) {
        object = repository.getOne(object.getId());
        if (object != null) {
            object.getMembers().size();
            object.getSessions().size();
            object.getCoaches().size();
        }
        return object;
    }

    @Override
    public void delete(TrainingGroup object) {
        object = repository.getOne(object.getId());
        if (object != null) {
            for (TrainingSession session : object.getSessions()) {
                session.removeGroup(object);
                sessionRepository.save(session);
            }
        }
        super.delete(object);
    }
}