package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.*;
import com.a31r.sport.coachassistant.core.model.service.CoachService;
import com.a31r.sport.coachassistant.core.model.service.DataService;
import com.a31r.sport.coachassistant.core.model.service.TrainingGroupService;
import com.a31r.sport.coachassistant.core.model.service.TrainingSessionService;
import com.a31r.sport.coachassistant.desktop.model.selector.TrainingGroupSelector;
import com.a31r.sport.coachassistant.desktop.view.util.DialogUtil;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by bahodurova on 1/19/2018.
 */
@Component
public class TrainingSessionEditor extends AbstractEditor<TrainingSession> {

    @Autowired
    private TrainingSessionService trainingSessionService;
    @Autowired
    private TrainingGroupService trainingGroupService;
    @Autowired
    private CoachService coachService;
    @Autowired
    private TrainingGroupSelector trainingGroupSelector;
    @Autowired
    private TrainingExerciseEditor exerciseEditor;
    @Autowired
    private TrainingSessionResultEditor resultEditor;

    private final TextField name = new TextField();
    private final ComboBox<Coach> coachComboBox = new ComboBox<>();
    private final Button schedule = new Button("Создать");
    private final Tab groups = new Tab("Группы");
    private final ListView<TrainingGroup> groupListView = new ListView<>();
    private final Button addGroupButton = new Button("Добавить");
    private final Button removeGroupButton = new Button("Удалить");
    private final Tab exercises = new Tab("Упражнения");
    private final ListView<TrainingExercise> exerciseListView = new ListView<>();
    private final Button addExerciseButton = new Button("Добавить");
    private final Button editExerciseButton = new Button("Изменить");
    private final Button removeExerciseButton = new Button("Удалить");
    private final Tab results = new Tab("Результаты");
    private final ListView<TrainingSessionResult> resultListView = new ListView<>();
    private final Button addResultButton = new Button("Добавить");
    private final Button editResultButton = new Button("Изменить");
    private final Button removeResultButton = new Button("Удалить");
    public TrainingSessionEditor() {
        gridPane.add(new Label("Название"), 0, 0);
        gridPane.add(name, 1, 0);
        gridPane.add(new Label("Тренер"), 0, 1);
        gridPane.add(coachComboBox, 1, 1);
        gridPane.add(new Label("График"), 0, 2);
        gridPane.add(schedule, 1, 2);
        groups.setContent(new VBox(groupListView,
                new ToolBar(addGroupButton, removeGroupButton)));
        exercises.setContent(new VBox(exerciseListView,
                new ToolBar(addExerciseButton, editExerciseButton, removeExerciseButton)));
        results.setContent(new VBox(resultListView,
                new ToolBar(addResultButton, editResultButton, removeResultButton)));
        tabPane.getTabs().addAll(groups, exercises, results);
        addGroupButton.setOnAction(event ->
                trainingGroupSelector.openDialog(selected -> {
                    trainingGroupService.includeMembers(selected);
                    object.addGroup(selected);
                    addToListView(groupListView, selected);
                })
        );

        removeGroupButton.setOnAction(event -> {
            TrainingGroup selected = groupListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                trainingGroupService.includeMembers(selected);
                object.removeGroup(selected);
                removeFromListView(groupListView, selected);
            }
        });
        addExerciseButton.setOnAction(event -> {
            TrainingExercise exercise = new TrainingExercise();
            exercise.setTrainingSession(object);
            PopupEditor.open(exerciseEditor, exercise, result -> {
                object.addTrainingExercise(exercise);
                addToListView(exerciseListView, exercise);
            });
        });
        editExerciseButton.setOnAction(event -> {
            TrainingExercise selected = exerciseListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                PopupEditor.open(exerciseEditor, selected, result -> {
                    object.addTrainingExercise(selected);
                    addToListView(exerciseListView, selected);
                });
            }
        });
        removeExerciseButton.setOnAction(event -> {
            TrainingExercise selected = exerciseListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                object.removeTrainingExercise(selected);
                removeFromListView(exerciseListView, selected);
            }
        });

        addResultButton.setOnAction(event -> {
            TrainingSessionResult sessionResult = new TrainingSessionResult();
            sessionResult.setTrainingSession(object);
            PopupEditor.open(resultEditor, sessionResult, result -> {
                object.addResult(result);
                addToListView(resultListView, result);
            });
        });
    }

    @PostConstruct
    private void init() {
        coachComboBox.setItems(FXCollections
                .observableArrayList(coachService.findAll()));
    }

    @Override
    public TrainingSession newObject() {
        return new TrainingSession();
    }

    @Override
    protected DataService<TrainingSession> getService() {
        return trainingSessionService;
    }

    @Override
    protected void beforeSave() {
        object.setName(name.getText());
        object.setCoach(coachComboBox.getValue());
    }

    @Override
    protected void setData() {
        name.setText(object.getName());
        if (object.getCoach() != null) {
            coachComboBox.getSelectionModel().select(object.getCoach());
        }
        groupListView.setItems(FXCollections.observableArrayList(object.getGroups()));
        exerciseListView.setItems(FXCollections.observableArrayList(object.getExercises()));
    }

    @Override
    protected void setDisabled(boolean disabled) {
        name.setDisable(disabled);
        coachComboBox.setDisable(disabled);
        groupListView.setDisable(disabled);
        schedule.setDisable(disabled);
        addGroupButton.setDisable(disabled);
        editExerciseButton.setDisable(disabled);
        removeGroupButton.setDisable(disabled);
        addExerciseButton.setDisable(disabled);
        removeExerciseButton.setDisable(disabled);
    }
}