package com.a31r.sport.core.model.repository;

import com.a31r.sport.core.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bahodurova on 1/10/2018.
 */
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
}
