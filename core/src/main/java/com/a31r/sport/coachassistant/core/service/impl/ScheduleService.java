package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.Schedule;
import com.a31r.sport.coachassistant.core.model.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class ScheduleService extends AbstractDataService<Schedule> {

    @Autowired
    private ScheduleRepository repository;

    @Override
    JpaRepository<Schedule, Long> getRepository() {
        return repository;
    }

}