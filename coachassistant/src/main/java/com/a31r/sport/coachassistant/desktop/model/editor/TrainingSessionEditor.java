package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.*;
import com.a31r.sport.coachassistant.core.service.TrainingGroupService;
import com.a31r.sport.coachassistant.core.service.TrainingSessionResultService;
import com.a31r.sport.coachassistant.core.service.TrainingSessionService;
import com.a31r.sport.coachassistant.core.service.DataService;
import com.a31r.sport.coachassistant.desktop.model.selector.CoachSelector;
import com.a31r.sport.coachassistant.desktop.model.selector.TrainingGroupSelector;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private TrainingGroupSelector trainingGroupSelector;
    @Autowired
    private TrainingExerciseEditor exerciseEditor;
    @Autowired
    private TrainingSessionResultEditor resultEditor;
    @Autowired
    private CoachSelector coachSelector;
    @Autowired
    private TrainingSessionResultService sessionResultService;

    private final TextField name = new TextField();
    private final ListView<Coach> coachListView = new ListView<>();
    private Button addCoach = new Button("Добавить");
    private Button removeCoach = new Button("Удалить");
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
        gridPane.add(new Label("Тренеры"), 0, 1);
        gridPane.add(new VBox(coachListView, new ToolBar(addCoach, removeCoach)), 1, 1);
        gridPane.add(new Label("График"), 0, 2);
        gridPane.add(schedule, 1, 2);

        groups.setContent(new VBox(groupListView,
                new ToolBar(addGroupButton, removeGroupButton)));

        exercises.setContent(new VBox(exerciseListView,
                new ToolBar(addExerciseButton, editExerciseButton, removeExerciseButton)));

        results.setContent(new VBox(resultListView,
                new ToolBar(addResultButton, editResultButton, removeResultButton)));

        tabPane.getTabs().addAll(groups, exercises, results);

        resultListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    editResultButton.setDisable(newValue == null);
                    removeResultButton.setDisable(newValue == null);
                });

        addGroupButton.setOnAction(event ->
                trainingGroupSelector.openDialog(selected -> {
                    trainingGroupService.initialize(selected);
                    object.addGroup(selected);
                    addToListView(groupListView, selected);
                })
        );

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

        removeGroupButton.setOnAction(event -> {
            TrainingGroup selected = groupListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
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
                sessionResultService.save(result);
                addToListView(resultListView, result);
            });
        });

        editResultButton.setOnAction(event -> {
            TrainingSessionResult selected = resultListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                PopupEditor.open(resultEditor, selected, result -> {
                    object.addResult(result);
                    sessionResultService.save(result);
                });
            }
        });

        removeResultButton.setOnAction(event -> {
            TrainingSessionResult selected = resultListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                object.removeResult(selected);
                removeFromListView(resultListView, selected);
            }
        });
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
    }

    @Override
    protected void fillWithObjectData() {
        setData(object.getName(), object.getCoaches(), object.getGroups(),
                object.getExercises(), object.getResults());
    }

    @Override
    protected void fillWithDefaultData() {
        setData("", new HashSet<>(), new HashSet<>(),
                new ArrayList<>(), new ArrayList<>());
    }

    private void setData(String theName, Set<Coach> coaches, Set<TrainingGroup> groups,
                         List<TrainingExercise> exercises, List<TrainingSessionResult> results) {
        name.setText(theName);
        coachListView.setItems(FXCollections.observableArrayList(coaches));
        groupListView.setItems(FXCollections.observableArrayList(groups));
        exerciseListView.setItems(FXCollections.observableArrayList(exercises));
        resultListView.setItems(FXCollections.observableArrayList(results));
    }

    @Override
    protected void setDisabled(boolean disabled) {
        name.setDisable(disabled);
        coachListView.setDisable(disabled);
        groupListView.setDisable(disabled);
        schedule.setDisable(disabled);
        addCoach.setDisable(disabled);
        removeCoach.setDisable(disabled);
        addGroupButton.setDisable(disabled);
        editExerciseButton.setDisable(disabled);
        removeGroupButton.setDisable(disabled);
        addExerciseButton.setDisable(disabled);
        removeExerciseButton.setDisable(disabled);
    }
}