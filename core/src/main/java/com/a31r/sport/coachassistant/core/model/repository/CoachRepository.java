package com.a31r.sport.coachassistant.core.model.repository;

import com.a31r.sport.coachassistant.core.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bahodurova on 1/7/2018.
 */
public interface CoachRepository extends JpaRepository<Coach, Long> {
}
