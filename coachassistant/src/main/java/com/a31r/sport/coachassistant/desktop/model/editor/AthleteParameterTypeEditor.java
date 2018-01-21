package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.AthleteParameterType;
import com.a31r.sport.coachassistant.core.model.service.AthleteParameterTypeService;
import com.a31r.sport.coachassistant.core.model.service.DataService;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bahodurova on 1/11/2018.
 */
@Service
public class AthleteParameterTypeEditor extends AbstractEditor<AthleteParameterType> {

    @Autowired
    private AthleteParameterTypeService athleteParameterTypeService;

    private final TextField name = new TextField();
    private final TextField unit = new TextField();

    public AthleteParameterTypeEditor() {
        gridPane.add(new Label("Название"), 0, 0);
        gridPane.add(name, 1, 0);
        gridPane.add(new Label("Еденица измерения"), 0, 1);
        gridPane.add(unit, 1, 1);
    }

    @Override
    protected DataService<AthleteParameterType> getService() {
        return athleteParameterTypeService;
    }

    @Override
    public void setData() {
        name.setText(object.getName());
        unit.setText(object.getUnit());
    }

    @Override
    protected void setDisabled(boolean disabled) {
        name.setDisable(disabled);
        unit.setDisable(disabled);
    }

    @Override
    protected void beforeSave() {
        object.setName(name.getText());
        object.setUnit(unit.getText());
    }

    @Override
    public AthleteParameterType newObject() {
        return new AthleteParameterType();
    }
}