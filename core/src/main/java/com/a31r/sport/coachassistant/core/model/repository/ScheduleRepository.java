package com.a31r.sport.coachassistant.core.model.repository;

import com.a31r.sport.coachassistant.core.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bahodurova on 1/9/2018.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
