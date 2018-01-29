package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.Athlete;
import com.a31r.sport.coachassistant.core.model.Coach;
import com.a31r.sport.coachassistant.core.model.TrainingGroup;
import com.a31r.sport.coachassistant.core.model.TrainingSession;
import com.a31r.sport.coachassistant.core.service.TrainingGroupService;
import com.a31r.sport.coachassistant.core.service.DataService;
import com.a31r.sport.coachassistant.desktop.model.selector.AthleteSelector;
import com.a31r.sport.coachassistant.desktop.model.selector.CoachSelector;
import com.a31r.sport.coachassistant.desktop.model.selector.Selector;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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
    private CoachSelector coachSelector;

    private ListView<Coach> coachListView = new ListView<>();
    private Button addCoach = new Button("Добавить");
    private Button removeCoach = new Button("Удалить");
    private Tab trainingSessionsTab = new Tab("Тренировки");
    private ListView<TrainingSession> trainingSessionList = new ListView<>();

    public TrainingGroupEditor() {
        gridPane.add(new Label("Тренеры"), 0, 1);
        gridPane.add(new VBox(coachListView, new ToolBar(addCoach, removeCoach)), 1, 1);

        addCoach.setOnAction(event ->
            coachSelector.openDialog(selected -> {
                object.addCoach(selected);
                addToListView(coachListView, selected);
            })
        );

        removeCoach.setOnAction(event -> {
            Coach selected = coachListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                object.removeCoach(selected);
                removeFromListView(coachListView, selected);
            }
        });

        trainingSessionsTab.setContent(trainingSessionList);
        tabPane.getTabs().add(trainingSessionsTab);
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
    }

    @Override
    protected void fillWithObjectData() {
        super.fillWithObjectData();
        setData(object.getCoaches(), object.getSessions());
    }

    @Override
    protected void fillWithDefaultData() {
        super.fillWithDefaultData();
        setData(new HashSet<>(), new HashSet<>());
    }

    private void setData(Set<Coach> coaches, Set<TrainingSession> sessions) {
        coachListView.setItems(FXCollections.observableArrayList(coaches));
        trainingSessionList.setItems(FXCollections.observableArrayList(sessions));
    }

    @Override
    protected void setDisabled(boolean disabled) {
        super.setDisabled(disabled);
        coachListView.setDisable(disabled);
        addCoach.setDisable(disabled);
        removeCoach.setDisable(disabled);
    }

    @Override
    Selector<Athlete> getSelector() {
        return athleteSelector;
    }
}