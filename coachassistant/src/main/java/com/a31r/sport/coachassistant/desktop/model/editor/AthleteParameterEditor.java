package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.AthleteParameter;
import com.a31r.sport.coachassistant.core.model.AthleteParameterType;
import com.a31r.sport.coachassistant.core.model.AthleteParameterValue;
import com.a31r.sport.coachassistant.core.service.AthleteParameterService;
import com.a31r.sport.coachassistant.core.service.AthleteParameterTypeService;
import com.a31r.sport.coachassistant.core.service.DataService;
import com.a31r.sport.coachassistant.desktop.view.util.DateTimeUtil;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

/**
 * Created by bahodurova on 1/17/2018.
 */
@Component
public class AthleteParameterEditor extends AbstractEditor<AthleteParameter> {

    @Autowired
    private AthleteParameterService service;
    @Autowired
    private AthleteParameterTypeService athleteParameterTypeService;

    private ComboBox<AthleteParameterType> parameterTypeComboBox = new ComboBox<>();
    private final TextField value = new TextField();
    private final TextField date = new TextField();

    public AthleteParameterEditor() {
        gridPane.add(new Label("Параметр"), 0, 0);
        gridPane.add(parameterTypeComboBox, 1, 0);
        gridPane.add(new Label("Значение"), 0, 1);
        gridPane.add(value, 1, 1);
        gridPane.add(new Label("Дата изменения"), 0, 2);
        gridPane.add(date, 1, 2);
        date.setText(LocalDate.now().format(DateTimeUtil.getDefaultFormatter()));
        date.setDisable(true);
    }

    @PostConstruct
    private void init() {
        parameterTypeComboBox.setItems(
                FXCollections.observableArrayList(athleteParameterTypeService.findAll()));
    }

    @Override
    public AthleteParameter newObject() {
        return new AthleteParameter();
    }

    @Override
    protected DataService<AthleteParameter> getService() {
        return service;
    }

    @Override
    protected void beforeSave() {
        object.setParameterType(parameterTypeComboBox.getValue());
        object.addValue(new AthleteParameterValue(LocalDate.now(), value.getText()));
    }

    @Override
    protected void fillWithObjectData() {
        setData(object.getParameterType(), object.getLatestValue().getDate(),
                object.getLatestValue().getValue());
    }

    @Override
    protected void fillWithDefaultData() {
        setData(null, LocalDate.now(), "");
    }

    private void setData(AthleteParameterType type, LocalDate localDate, String paramValue) {
        value.setText(paramValue);
        date.setText(localDate.format(DateTimeUtil.getDefaultFormatter()));
        parameterTypeComboBox.getSelectionModel().select(type);
    }

    @Override
    protected void setDisabled(boolean disabled) {
        value.setDisable(disabled);
        parameterTypeComboBox.setDisable(disabled);
    }
}