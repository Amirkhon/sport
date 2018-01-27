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

    @Override
    public Coach initialize(Coach object) {
        Coach coach = repository.getOne(object.getId());
        if (coach != null) {
            coach.getTrainingSessions().size();
            coach.getTrainingGroups().size();
            return coach;
        }
        return object;
    }

    @Transactional
    @Override
    public void delete(Coach object) {
        Coach coach = repository.getOne(object.getId());

        if (coach != null) {
            for (TrainingSession session : object.getTrainingSessions()) {
                session.setCoach(null);
                sessionRepository.save(session);
            }

            for (TrainingGroup group : object.getTrainingGroups()) {
                group.setCoach(null);
                groupRepository.save(group);
            }
            object = coach;
        }

        super.delete(object);
    }
}