package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.Coach;
import com.a31r.sport.coachassistant.core.model.service.CoachService;
import com.a31r.sport.coachassistant.core.model.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by bahodurova on 1/21/2018.
 */
@Component
public class CoachEditor extends AbstractUserEditor<Coach> {

    @Autowired
    private CoachService service;

    @Override
    public Coach newObject() {
        return new Coach();
    }

    @Override
    protected DataService<Coach> getService() {
        return service;
    }
}
