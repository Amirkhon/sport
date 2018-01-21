package com.a31r.sport.coachassistant.desktop.model.selector;

import com.a31r.sport.coachassistant.core.model.Exercise;
import com.a31r.sport.coachassistant.core.model.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Component
public class ExerciseSelector extends AbstractSelector<Exercise> {

    @Autowired
    private ExerciseService service;

    @Override
    List<Exercise> getItems() {
        return service.findAll();
    }
}
