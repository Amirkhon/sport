package com.a31r.sport.coachassistant.desktop.model.selector;

import com.a31r.sport.coachassistant.core.model.AthleteParameterType;
import com.a31r.sport.coachassistant.core.service.AthleteParameterTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Component
public class AthleteParameterTypeSelector extends AbstractSelector<AthleteParameterType> {

    @Autowired
    private AthleteParameterTypeService service;

    @Override
    List<AthleteParameterType> getItems() {
        return service.findAll();
    }
}