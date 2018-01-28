package com.a31r.sport.coachassistant.core.service.impl;

import com.a31r.sport.coachassistant.core.model.AbstractEntity;
import com.a31r.sport.coachassistant.core.service.DataService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
public abstract class AbstractDataService<T extends AbstractEntity> implements DataService<T> {

    abstract JpaRepository<T, Long> getRepository();
    @Override
    public T findById(Long id) {
        return getRepository().getOne(id);
    }

    @Override
    public T initialize(T object) {
        return object;
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public T save(T object) {
        return getRepository().save(object);
    }

    @Override
    public void delete(T object) {
        getRepository().delete(object);
    }

}