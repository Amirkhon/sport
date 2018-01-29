package com.a31r.sport.coachassistant.desktop.model.selector;

import com.a31r.sport.coachassistant.core.model.TrainingSession;
import com.a31r.sport.coachassistant.core.service.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Component
public class TrainingSessionSelector extends AbstractSelector<TrainingSession> {

    @Autowired
    private TrainingSessionService service;

    @Override
    List<TrainingSession> getItems() {
        return service.findAll();
    }
}
