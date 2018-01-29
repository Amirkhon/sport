package com.a31r.sport.coachassistant.desktop.model.selector;

import com.a31r.sport.coachassistant.core.model.UserPropertyType;
import com.a31r.sport.coachassistant.core.service.UserPropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Component
public class UserPropertyTypeSelector extends AbstractSelector<UserPropertyType> {

    @Autowired
    private UserPropertyTypeService service;

    @Override
    List<UserPropertyType> getItems() {
        return service.findAll();
    }
}
