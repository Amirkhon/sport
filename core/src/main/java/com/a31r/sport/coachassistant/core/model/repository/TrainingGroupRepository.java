package com.a31r.sport.coachassistant.core.model.repository;

import com.a31r.sport.coachassistant.core.model.Athlete;
import com.a31r.sport.coachassistant.core.model.Coach;
import com.a31r.sport.coachassistant.core.model.TrainingGroup;
import com.a31r.sport.coachassistant.core.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by bahodurova on 1/7/2018.
 */
public interface TrainingGroupRepository extends JpaRepository<TrainingGroup, Long> {
    List<TrainingGroup> findAllByCoachesContains(Coach coach);
    List<TrainingGroup> findAllByMembersContains(Athlete athlete);
    List<TrainingGroup> findAllBySessionsContains(TrainingSession session);
}
