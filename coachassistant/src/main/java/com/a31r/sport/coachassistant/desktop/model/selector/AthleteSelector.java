package com.a31r.sport.coachassistant.desktop.model.selector;

import com.a31r.sport.coachassistant.core.model.Athlete;
import com.a31r.sport.coachassistant.core.model.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Component
public class AthleteSelector extends AbstractSelector<Athlete> {

    @Autowired
    private AthleteService service;

    @Override
    List<Athlete> getItems() {
        return service.findAll();
    }
}
