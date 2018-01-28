package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.Coach;
import com.a31r.sport.coachassistant.core.model.TrainingGroup;
import com.a31r.sport.coachassistant.core.model.TrainingSession;
import com.a31r.sport.coachassistant.core.model.repository.CoachRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingGroupRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingSessionRepository;
import com.a31r.sport.coachassistant.core.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class CoachServiceImpl extends AbstractDataService<Coach> implements CoachService {

    @Autowired
    private CoachRepository repository;
    @Autowired
    private TrainingSessionRepository sessionRepository;
    @Autowired
    private TrainingGroupRepository groupRepository;

    @Override
    JpaRepository<Coach, Long> getRepository() {
        return repository;
    }

    @Transactional
    @Override
    public Coach initialize(Coach object) {
        object = repository.getOne(object.getId());
        if (object != null) {
            object.getTrainingSessions().size();
            object.getTrainingGroups().size();
        }
        return object;
    }

    @Transactional
    @Override
    public void delete(Coach object) {
        object = repository.getOne(object.getId());
        if (object != null) {
            for (TrainingSession session : object.getTrainingSessions()) {
                session.removeCoach(object);
                sessionRepository.save(session);
            }

            for (TrainingGroup group : object.getTrainingGroups()) {
                group.removeCoach(object);
                groupRepository.save(group);
            }
        }

        super.delete(object);
    }
}