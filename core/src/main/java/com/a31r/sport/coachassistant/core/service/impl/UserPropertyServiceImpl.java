package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.UserProperty;
import com.a31r.sport.coachassistant.core.model.repository.UserPropertyRepository;
import com.a31r.sport.coachassistant.core.service.UserPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class UserPropertyServiceImpl extends AbstractDataService<UserProperty> implements UserPropertyService {


    @Autowired
    private UserPropertyRepository repository;

    @Override
    JpaRepository<UserProperty, Long> getRepository() {
        return repository;
    }

}
