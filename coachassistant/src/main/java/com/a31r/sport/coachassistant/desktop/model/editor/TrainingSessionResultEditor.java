package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.*;
import com.a31r.sport.coachassistant.core.model.service.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;

/**
 * Created by bahodurova on 1/21/2018.
 */
@Component
public class TrainingSessionResultEditor extends AbstractEditor<TrainingSessionResult> {

    @Autowired
    private TrainingSessionResultService service;
    @Autowired
    private TrainingGroupService trainingGroupService;
    @Autowired
    private TrainingSessionService trainingSessionService;
    @Autowired
    private CoachService coachService;

    private TextField session = new TextField();
    private ComboBox<Coach> coachComboBox = new ComboBox<>();
    private DatePicker date = new DatePicker();
    private ScrollPane scrollPane = new ScrollPane();
    private ComboBox<TrainingExercise> exerciseComboBox = new ComboBox<>();
//    private GridPane athleteListGrid = new GridPane();
    private GridPane resultsGrid = new GridPane();
    private List<Result> results = new ArrayList<>();

    public TrainingSessionResultEditor() {
        gridPane.add(new Label("Тренировка"), 0,0);
        gridPane.add(session, 1, 0);
        gridPane.add(new Label("Дата"), 0,1);
        gridPane.add(date, 1, 1);
        gridPane.add(new Label("Заполнил"), 0,2);
        gridPane.add(coachComboBox, 1, 2);
        gridPane.add(new Label("Результаты по упражнению"), 0,3);
        gridPane.add(exerciseComboBox, 1, 3);
        session.setDisable(true);
        resultsGrid.setVgap(10);
        resultsGrid.setHgap(10);
        exerciseComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                for (Result result : results) {
                    result.addInputField(resultsGrid, newValue);
                }
            }
        });
    }

    @PostConstruct
    private void init () {
        coachComboBox.setItems(FXCollections.observableArrayList(coachService.findAll()));
    }

    @Override
    public TrainingSessionResult newObject() {
        return new TrainingSessionResult();
    }

    @Override
    protected DataService<TrainingSessionResult> getService() {
        return service;
    }

    @Override
    protected void beforeSave() {
        object.setDate(date.getValue());
        object.setAuthor(coachComboBox.getValue());
        object.setAttendances(getAttendances());
        object.setResults(getExerciseResults());
    }

    @Override
    protected void setData() {
        session.setText(object.getTrainingSession().toString());
        if (object.getAuthor() != null) {
            coachComboBox.getSelectionModel().select(object.getAuthor());
        }
        date.setValue(object.getDate());
    }

    @Override
    protected void setDisabled(boolean disabled) {
        coachComboBox.setDisable(disabled);
        date.setDisable(disabled);
        exerciseComboBox.setDisable(disabled);
    }

    @Override
    protected void beforeShow() {
        resultsGrid.getChildren().clear();
        resultsGrid.add(new Label("Ф.И.О."), 0,0);
        resultsGrid.add(new Label("Б/Н"), 1,0);
        resultsGrid.add(new Label("Результат"), 2,0);
        resultsGrid.add(new Label("Повторы"), 3,0);
        resultsGrid.add(new Label("За время(мин)"), 4,0);
        List<Athlete> athletes = new ArrayList<>();
        trainingSessionService.includeMembers(object.getTrainingSession());
        for (TrainingGroup group : object.getTrainingSession().getGroups()) {
            trainingGroupService.includeMembers(group);
            for (User user : group.getMembers()) {
                if (user instanceof Athlete) {
                    athletes.add((Athlete) user);
                }
            }
        }
        List<TrainingExercise> exercises = object.getTrainingSession().getExercises();
        exerciseComboBox.setItems(FXCollections.observableArrayList(exercises));
        for (int i = 0; i < athletes.size(); i++) {
            results.add(new Result(i + 1, athletes.get(i), exercises));
        }
        for (Result result : results) {
            result.addToList(resultsGrid);
        }
    }

    @Override
    protected Parent view() {
        scrollPane.setContent(resultsGrid);
        VBox vBox = new VBox(gridPane, scrollPane);
        vBox.setSpacing(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        view.setCenter(vBox);
        return view;
    }

    private List<ExerciseResult> getExerciseResults() {
        List<ExerciseResult> exerciseResults = new ArrayList<>();
        for (Result result : results) {
            exerciseResults.addAll(result.getResults());
        }
        return exerciseResults;
    }

    private List<AthleteAttendance> getAttendances() {
        List<AthleteAttendance> attendances = new ArrayList<>();
        for (Result result : results) {
            attendances.add(result.getAttendance());
        }
        return attendances;
    }

    private class Result {
        private final int rowIndex;
        private final Athlete athlete;
        private CheckBox attendance = new CheckBox();
        private Map<TrainingExercise, TextField> values = new HashMap<>();
        private Map<TrainingExercise, TextField> repeats = new HashMap<>();
        private Map<TrainingExercise, TextField> durations = new HashMap<>();

        public Result(int rowIndex, Athlete athlete, List<TrainingExercise> exercises) {
            this.rowIndex = rowIndex;
            this.athlete = athlete;
            attendance.setSelected(true);
            for (TrainingExercise exercise : exercises) {
                values.put(exercise, new TextField());
                repeats.put(exercise, new TextField());
                durations.put(exercise, new TextField());
            }
        }

        private TextField getField(TrainingExercise exercise, Map<TrainingExercise, TextField> fields) {
            TextField textField = fields.get(exercise);
            if (textField == null) {
                textField = new TextField();
            }
            textField.setDisable(!attendance.isSelected());
            textField.setMaxWidth(50);
            return textField;
        }

        public List<ExerciseResult> getResults() {
            List<ExerciseResult> results = new ArrayList<>();
            if (attendance.isSelected()) {
                for (Map.Entry<TrainingExercise, TextField> entry : values.entrySet()) {
                    int repeat = Integer.parseInt(repeats.get(entry.getKey()).getText());
                    Duration duration = Duration.ofMinutes(Integer.parseInt(durations.get(entry.getKey()).getText()));
                    results.add(new ExerciseResult(athlete, entry.getKey(), repeat, duration, entry.getValue().getText()));
                }
            }
            return results;
        }

        public AthleteAttendance getAttendance() {
            return new AthleteAttendance(athlete, object, attendance.isSelected());
        }

        public void addToList(GridPane gridPane) {
            gridPane.add(new Label(athlete.shortFullName()), 0, rowIndex);
            gridPane.add(attendance, 1, rowIndex);
        }
        public void addInputField(GridPane gridPane, TrainingExercise exercise) {
            gridPane.add(getField(exercise, values), 2, rowIndex);
            gridPane.add(getField(exercise, repeats), 3, rowIndex);
            gridPane.add(getField(exercise, durations), 4, rowIndex);
        }
    }
}