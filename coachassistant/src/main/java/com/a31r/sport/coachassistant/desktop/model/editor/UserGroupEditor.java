package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.User;
import com.a31r.sport.coachassistant.core.model.UserGroup;
import com.a31r.sport.coachassistant.core.model.service.DataService;
import com.a31r.sport.coachassistant.core.model.service.UserGroupService;
import com.a31r.sport.coachassistant.desktop.model.selector.Selector;
import com.a31r.sport.coachassistant.desktop.model.selector.UserSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Component
public class UserGroupEditor extends AbstractUserGroupEditor<UserGroup, User> {

    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private UserSelector userSelector;

    @Override
    public UserGroup newObject() {
        return new UserGroup("Группа");
    }

    @Override
    protected DataService<UserGroup> getService() {
        return userGroupService;
    }

    @Override
    Selector getSelector() {
        return userSelector;
    }
}