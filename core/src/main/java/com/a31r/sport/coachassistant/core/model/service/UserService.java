package com.a31r.sport.coachassistant.core.model.service;

import com.a31r.sport.coachassistant.core.model.User;
import com.a31r.sport.coachassistant.core.model.repository.UserGroupRepository;
import com.a31r.sport.coachassistant.core.model.repository.UserPropertyRepository;
import com.a31r.sport.coachassistant.core.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class UserService extends AbstractDataService<User>{

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserGroupRepository groupRepository;
    @Autowired
    private UserPropertyRepository propertyRepository;

    @Override
    JpaRepository<User, Long> getRepository() {
        return repository;
    }

    @Override
    public void includeMembers(User object) {
        object.setGroups(new HashSet<>(groupRepository.findAllByMembersContains(object)));
        object.setProperties(propertyRepository.findAllByUser(object));
    }
}