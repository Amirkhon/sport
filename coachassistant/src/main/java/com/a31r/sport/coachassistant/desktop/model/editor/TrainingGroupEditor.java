package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.Athlete;
import com.a31r.sport.coachassistant.core.model.Coach;
import com.a31r.sport.coachassistant.core.model.TrainingGroup;
import com.a31r.sport.coachassistant.core.model.TrainingSession;
import com.a31r.sport.coachassistant.core.model.service.CoachService;
import com.a31r.sport.coachassistant.core.model.service.DataService;
import com.a31r.sport.coachassistant.core.model.service.TrainingGroupService;
import com.a31r.sport.coachassistant.desktop.model.selector.AthleteSelector;
import com.a31r.sport.coachassistant.desktop.model.selector.Selector;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by bahodurova on 1/16/2018.
 */
@Component
public class TrainingGroupEditor extends AbstractUserGroupEditor<TrainingGroup, Athlete> {

    @Autowired
    private TrainingGroupService trainingGroupService;
    @Autowired
    private AthleteSelector athleteSelector;
    @Autowired
    private CoachService coachService;

    private ComboBox<Coach> coachComboBox = new ComboBox<>();
    private Tab trainingSessionsTab = new Tab("Тренировки");
    private ListView<TrainingSession> trainingSessionList = new ListView<>();
    private Button addTrainingSession = new Button("Добавить");
    private Button removeTrainingSession = new Button("Удалить");

    public TrainingGroupEditor() {
        gridPane.add(new Label("Тренер"), 0, 1);
        gridPane.add(coachComboBox, 1, 1);
        trainingSessionsTab.setContent(new VBox(trainingSessionList,
                new ToolBar(addTrainingSession, removeTrainingSession)));
        tabPane.getTabs().add(trainingSessionsTab);
    }

    @PostConstruct
    private void init() {
        coachComboBox.setItems(FXCollections.observableArrayList(coachService.findAll()));
    }

    @Override
    public TrainingGroup newObject() {
        return new TrainingGroup();
    }

    @Override
    protected DataService<TrainingGroup> getService() {
        return trainingGroupService;
    }

    @Override
    protected void beforeSave() {
        super.beforeSave();
        object.setCoach(coachComboBox.getValue());
    }

    @Override
    protected void setData() {
        super.setData();
        if (object.getCoach() != null) {
            coachComboBox.getSelectionModel().select(object.getCoach());
        }
        trainingSessionList.setItems(FXCollections.observableArrayList(object.getSessions()));
    }

    @Override
    protected void setDisabled(boolean disabled) {
        super.setDisabled(disabled);
        coachComboBox.setDisable(disabled);
        addTrainingSession.setDisable(disabled);
        removeTrainingSession.setDisable(disabled);
    }

    @Override
    Selector<Athlete> getSelector() {
        return athleteSelector;
    }
}