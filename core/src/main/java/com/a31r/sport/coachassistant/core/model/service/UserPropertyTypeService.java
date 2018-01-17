package com.a31r.sport.coachassistant.core.model.service;

import com.a31r.sport.coachassistant.core.model.UserPropertyType;
import com.a31r.sport.coachassistant.core.model.repository.UserPropertyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Service
public class UserPropertyTypeService extends AbstractDataService<UserPropertyType> {

    @Autowired
    private UserPropertyTypeRepository repository;

    @Override
    JpaRepository<UserPropertyType, Long> getRepository() {
        return repository;
    }
}
