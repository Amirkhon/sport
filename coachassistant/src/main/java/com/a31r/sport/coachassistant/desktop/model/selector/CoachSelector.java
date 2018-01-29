package com.a31r.sport.coachassistant.desktop.model.selector;

import com.a31r.sport.coachassistant.core.model.Coach;
import com.a31r.sport.coachassistant.core.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Component
public class CoachSelector extends AbstractSelector<Coach> {

    @Autowired
    private CoachService service;

    @Override
    List<Coach> getItems() {
        return service.findAll();
    }
}
