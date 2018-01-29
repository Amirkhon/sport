package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.Athlete;
import com.a31r.sport.coachassistant.core.model.AthleteParameter;
import com.a31r.sport.coachassistant.core.service.AthleteService;
import com.a31r.sport.coachassistant.core.service.DataService;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bahodurova on 1/11/2018.
 */
@Service
public class AthleteEditor extends AbstractUserEditor<Athlete> {

    @Autowired
    private AthleteService athleteService;
    @Autowired
    private AthleteParameterEditor parameterEditor;

    private final Tab parameterTab = new Tab("Параметры");
    private final ListView<AthleteParameter> parameters = new ListView<>();

    protected final Button addParameterButton = new Button( "Добавить");
    protected final Button editParameterButton = new Button("Изменить");
    protected final Button removeParameterButton = new Button("Удалить");

    public AthleteEditor() {
        addParameterButton.setOnAction(event -> {
            openParameterDialog(parameterEditor.newObject());
        });
        editParameterButton.setOnAction(event -> {
            AthleteParameter parameter = parameters.getSelectionModel().getSelectedItem();
            if (parameter != null) {
                openParameterDialog(parameter);
            }
        });
        removeParameterButton.setOnAction(event -> {
            AthleteParameter parameter = parameters.getSelectionModel().getSelectedItem();
            if (parameter != null) {
                object.removeParameter(parameter);
                removeFromListView(parameters, parameter);
            }
        });
    }

    private void openParameterDialog(AthleteParameter parameter) {
        PopupEditor.open(parameterEditor, parameter, result -> {
            object.addParameter(result);
            addToListView(parameters, result);
        });
    }

    @Override
    protected void fillWithObjectData() {
        super.fillWithObjectData();
        setData(object.getParameters());
    }

    @Override
    protected void fillWithDefaultData() {
        super.fillWithDefaultData();
        setData(new ArrayList<>());
    }

    private void setData(List<AthleteParameter> parameterList) {
        parameters.setItems(FXCollections.observableArrayList(parameterList));
    }

    @Override
    protected void beforeShow() {
        super.beforeShow();
        parameterTab.setContent(new VBox(parameters,
                new ToolBar(addParameterButton, editParameterButton, removeParameterButton)));
        tabPane.getTabs().add(parameterTab);
    }

    @Override
    protected DataService<Athlete> getService() {
        return athleteService;
    }

    @Override
    public Athlete newObject() {
        return new Athlete();
    }

    @Override
    public void setDisabled(boolean disabled) {
        super.setDisabled(disabled);
        parameters.setDisable(disabled);
        addParameterButton.setDisable(disabled);
        editParameterButton.setDisable(disabled);
        removeParameterButton.setDisable(disabled);
    }
}