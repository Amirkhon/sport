package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.User;
import com.a31r.sport.coachassistant.core.model.repository.UserRepository;
import com.a31r.sport.coachassistant.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class UserServiceImpl extends AbstractDataService<User> implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    JpaRepository<User, Long> getRepository() {
        return repository;
    }

    @Transactional
    @Override
    public User initialize(User object) {
        object = repository.getOne(object.getId());
        if (object != null) {
            object.getGroups().size();
            object.getProperties().size();
        }
        return object;
    }
}