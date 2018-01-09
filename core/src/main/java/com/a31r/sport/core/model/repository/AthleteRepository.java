package com.a31r.sport.core.model.repository;

import com.a31r.sport.core.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bahodurova on 1/7/2018.
 */
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
