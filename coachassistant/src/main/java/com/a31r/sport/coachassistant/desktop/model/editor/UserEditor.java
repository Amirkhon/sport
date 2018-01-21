package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.User;
import com.a31r.sport.coachassistant.core.model.service.DataService;
import com.a31r.sport.coachassistant.core.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by bahodurova on 1/11/2018.
 */
@Component
public class UserEditor extends AbstractUserEditor<User> {

    @Autowired
    private UserService userService;

    @Override
    protected DataService<User> getService() {
        return userService;
    }

    @Override
    public User newObject() {
        return new User();
    }

}