package com.a31r.sport.coachassistant.core.model.repository;

import com.a31r.sport.coachassistant.core.model.User;
import com.a31r.sport.coachassistant.core.model.UserProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by bahodurova on 1/7/2018.
 */
public interface UserPropertyRepository extends JpaRepository<UserProperty, Long> {
    List<UserProperty> findAllByUser(User user);
}
