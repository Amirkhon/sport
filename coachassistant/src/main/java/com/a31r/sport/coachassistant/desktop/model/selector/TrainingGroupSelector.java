package com.a31r.sport.coachassistant.desktop.model.selector;

import com.a31r.sport.coachassistant.core.model.TrainingGroup;
import com.a31r.sport.coachassistant.core.service.TrainingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Component
public class TrainingGroupSelector extends AbstractSelector<TrainingGroup> {

    @Autowired
    private TrainingGroupService service;

    @Override
    List<TrainingGroup> getItems() {
        return service.findAll();
    }
}
