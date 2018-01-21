package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.Exercise;
import com.a31r.sport.coachassistant.core.model.service.DataService;
import com.a31r.sport.coachassistant.core.model.service.ExerciseService;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Component
public class ExerciseEditor extends AbstractEditor<Exercise> {

    @Autowired
    private ExerciseService exerciseService;

    private TextField name = new TextField();
    private TextField unit = new TextField();

    public ExerciseEditor() {
        gridPane.add(new Label("Название"), 0, 0);
        gridPane.add(name, 1, 0);
        gridPane.add(new Label("Еденица измерения"), 0, 1);
        gridPane.add(unit, 1, 1);
    }

    @Override
    public Exercise newObject() {
        return new Exercise();
    }

    @Override
    protected DataService<Exercise> getService() {
        return exerciseService;
    }

    @Override
    protected void beforeSave() {
        object.setName(name.getText());
        object.setUnit(unit.getText());
    }

    @Override
    protected void setData() {
        name.setText(object.getName());
        unit.setText(object.getUnit());
    }

    @Override
    protected void setDisabled(boolean disabled) {
        name.setDisable(disabled);
        unit.setDisable(disabled);
    }
}
