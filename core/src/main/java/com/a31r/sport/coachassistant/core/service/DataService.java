package com.a31r.sport.coachassistant.core.service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
public interface DataService<T> {
    T findById(Long id);
    @Transactional
    T initialize(T object);
    List<T> findAll();
    T save(T object);
    @Transactional
    void delete(T object);
}
