package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.AthleteAttendance;
import com.a31r.sport.coachassistant.core.model.repository.AthleteAttendanceRepository;
import com.a31r.sport.coachassistant.core.service.AthleteAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/21/2018.
 */
@Service
public class AthleteAttendanceServiceImpl extends AbstractDataService<AthleteAttendance> implements AthleteAttendanceService {

    @Autowired
    private AthleteAttendanceRepository repository;

    @Override
    JpaRepository<AthleteAttendance, Long> getRepository() {
        return repository;
    }
}
