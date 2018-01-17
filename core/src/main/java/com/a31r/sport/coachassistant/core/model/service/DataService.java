package com.a31r.sport.coachassistant.core.model.service;

import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
public interface DataService<T> {
    T findById(Long id);
    void includeMembers(T object);
    List<T> findAll();
    T save(T object);
    void delete(T object);
}
