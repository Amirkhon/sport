package com.a31r.sport.coachassistant.core.model.repository;

import com.a31r.sport.coachassistant.core.model.User;
import com.a31r.sport.coachassistant.core.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bahodurova on 1/7/2018.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByGroupsContains(UserGroup group);
}
