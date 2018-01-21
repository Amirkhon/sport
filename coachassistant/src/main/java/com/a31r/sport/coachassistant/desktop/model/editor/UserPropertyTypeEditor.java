package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.UserPropertyType;
import com.a31r.sport.coachassistant.core.model.service.DataService;
import com.a31r.sport.coachassistant.core.model.service.UserPropertyTypeService;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Component
public class UserPropertyTypeEditor extends AbstractEditor<UserPropertyType> {

    @Autowired
    private UserPropertyTypeService userPropertyTypeService;

    private TextField name = new TextField();

    public UserPropertyTypeEditor() {
        gridPane.add(new Label("Название"), 0, 0);
        gridPane.add(name, 1, 0);
    }

    @Override
    public UserPropertyType newObject() {
        return new UserPropertyType();
    }

    @Override
    protected DataService<UserPropertyType> getService() {
        return userPropertyTypeService;
    }

    @Override
    protected void beforeSave() {
        object.setName(name.getText());
    }

    @Override
    protected void setData() {
        name.setText(object.getName());
    }

    @Override
    protected void setDisabled(boolean disabled) {
        name.setDisable(disabled);
    }
}
