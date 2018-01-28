package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.UserGroup;
import com.a31r.sport.coachassistant.core.model.repository.UserGroupRepository;
import com.a31r.sport.coachassistant.core.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class UserGroupServiceImpl extends AbstractDataService<UserGroup> implements UserGroupService {

    @Autowired
    private UserGroupRepository repository;

    @Override
    JpaRepository<UserGroup, Long> getRepository() {
        return repository;
    }

    @Override
    public UserGroup initialize(UserGroup object) {
        object = repository.getOne(object.getId());
        if (object != null) {
            object.getMembers().size();
        }
        return object;
    }
}