package com.a31r.sport.coachassistant.core.model.service;

import com.a31r.sport.coachassistant.core.model.UserGroup;
import com.a31r.sport.coachassistant.core.model.repository.UserGroupRepository;
import com.a31r.sport.coachassistant.core.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class UserGroupService extends AbstractDataService<UserGroup>{

    @Autowired
    private UserGroupRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Override
    JpaRepository<UserGroup, Long> getRepository() {
        return repository;
    }

    @Override
    public void includeMembers(UserGroup object) {
        object.setMembers(new HashSet<>(userRepository.findAllByGroupsContains(object)));
    }
}