package com.a31r.sport.coachassistant.desktop.model.selector;

import com.a31r.sport.coachassistant.core.model.User;
import com.a31r.sport.coachassistant.core.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Component
public class UserSelector extends AbstractSelector<User> {

    @Autowired
    private UserService service;

    @Override
    List<User> getItems() {
        return service.findAll();
    }
}
