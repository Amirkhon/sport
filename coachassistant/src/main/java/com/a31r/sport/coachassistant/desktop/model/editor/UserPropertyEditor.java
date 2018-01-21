package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.UserProperty;
import com.a31r.sport.coachassistant.core.model.UserPropertyType;
import com.a31r.sport.coachassistant.core.model.repository.UserPropertyTypeRepository;
import com.a31r.sport.coachassistant.core.model.service.DataService;
import com.a31r.sport.coachassistant.core.model.service.UserPropertyService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/12/2018.
 */
@Service
public class UserPropertyEditor extends AbstractEditor<UserProperty> {

    @Autowired
    private UserPropertyService userPropertyService;

    @Autowired
    private UserPropertyTypeRepository repository;

    private final ObservableList<UserPropertyType> propertyTypes = FXCollections.observableArrayList();
    private final ComboBox<UserPropertyType> userPropertyTypeComboBox = new ComboBox<>();
    private final TextField value = new TextField();

    public UserPropertyEditor() {
        gridPane.add(new Label("Тип"), 0, 0);
        gridPane.add(userPropertyTypeComboBox, 1, 0);
        gridPane.add(new Label("Значение"), 0, 1);
        gridPane.add(value, 1, 1);
        userPropertyTypeComboBox.setItems(propertyTypes);
    }

    @Override
    protected void beforeSave() {
        object.setPropertyType(userPropertyTypeComboBox.getValue());
        object.setValue(value.getText());
    }

    @Override
    protected void setData() {
        userPropertyTypeComboBox.setValue(object.getPropertyType());
        value.setText(object.getValue());
    }

    @Override
    protected void setDisabled(boolean disabled) {
        userPropertyTypeComboBox.setDisable(disabled);
        value.setDisable(disabled);
    }

    @Override
    protected void beforeShow() {
        super.beforeShow();
        propertyTypes.clear();
        propertyTypes.addAll(repository.findAll());
    }

    @Override
    protected DataService<UserProperty> getService() {
        return userPropertyService;
    }

    @Override
    public UserProperty newObject() {
        return new UserProperty();
    }
}