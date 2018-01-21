package com.a31r.sport.coachassistant.desktop.model.selector;

import com.a31r.sport.coachassistant.core.model.UserGroup;
import com.a31r.sport.coachassistant.core.model.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Component
public class UserGroupSelector extends AbstractSelector<UserGroup> {

    @Autowired
    private UserGroupService userGroupService;

    @Override
    List<UserGroup> getItems() {
        return userGroupService.findAll();
    }
}