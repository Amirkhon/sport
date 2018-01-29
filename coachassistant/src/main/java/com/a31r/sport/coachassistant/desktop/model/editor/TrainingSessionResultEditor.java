package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.*;
import com.a31r.sport.coachassistant.core.service.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by bahodurova on 1/21/2018.
 */
@Component
public class TrainingSessionResultEditor extends AbstractEditor<TrainingSessionResult> {

    @Autowired
    private TrainingSessionResultService service;
    @Autowired
    private TrainingSessionService trainingSessionService;
    @Autowired
    private TrainingGroupService trainingGroupService;

    private TextField session = new TextField();
    private ComboBox<Coach> coachComboBox = new ComboBox<>();
    private DatePicker date = new DatePicker();
    private ScrollPane scrollPane = new ScrollPane();
    private ComboBox<TrainingExercise> exerciseComboBox = new ComboBox<>();
    private GridPane resultsGrid = new GridPane();
    private SortedMap<Athlete, Result> results = new TreeMap<>(Comparator.comparing(Athlete::shortFullName));

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
                for (Result result : results.values()) {
                    result.addInputField(resultsGrid, newValue);
                }
            }
        });
        scrollPane.setMinHeight(350);
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
        for (AthleteAttendance attendance : getAttendances()) {
            object.addAttendance(attendance);
        }
        for (ExerciseResult result : getExerciseResults()) {
            object.addResult(result);
        }
        object.setAttendances(getAttendances());
        object.setResults(getExerciseResults());
    }

    @Override
    protected void fillWithObjectData() {
        setData(object.getAuthor(), object.getDate());
    }

    @Override
    protected void fillWithDefaultData() {
        setData(null, LocalDate.now());
    }

    private void setData(Coach coach, LocalDate localDate) {
        date.setValue(localDate);
        results.clear();
        TrainingSession trainingSession = trainingSessionService.initialize(object.getTrainingSession());
        exerciseComboBox.setItems(FXCollections.observableArrayList(trainingSession.getExercises()));
        exerciseComboBox.getSelectionModel().clearSelection();
        resultsGrid.getChildren().clear();
        session.setText(trainingSession.toString());
        coachComboBox.setItems(FXCollections.observableArrayList(trainingSession.getCoaches()));
        coachComboBox.getSelectionModel().select(coach);
        int counter = 1;
        for (TrainingGroup group : trainingSession.getGroups()) {
            group = trainingGroupService.initialize(group);
            for (User user : group.getMembers()) {
                if (user instanceof Athlete) {
                    results.put((Athlete) user, new Result(counter, (Athlete) user, trainingSession.getExercises()));
                }
            }
        }
        for (AthleteAttendance attendance : object.getAttendances()) {
            results.get(attendance.getAthlete()).setAttendance(attendance);
        }
        for (ExerciseResult result : object.getResults()) {
            results.get(result.getAthlete()).setExerciseResult(result);
        }
        for (Map.Entry<Athlete, Result> entry : results.entrySet()) {
            entry.getValue().addToList(resultsGrid);
        }
    }

    @Override
    protected void setDisabled(boolean disabled) {
        coachComboBox.setDisable(disabled);
        date.setDisable(disabled);
        exerciseComboBox.setDisable(disabled);
    }

    @Override
    protected void beforeShow() {
        resultsGrid.add(new Label("Ф.И.О."), 0,0);
        resultsGrid.add(new Label("Б/Н"), 1,0);
        resultsGrid.add(new Label("Результат"), 2,0);
        resultsGrid.add(new Label("Повторы"), 3,0);
        resultsGrid.add(new Label("За время(мин)"), 4,0);
    }

    @Override
    protected Parent view() {
        scrollPane.setContent(resultsGrid);
        VBox vBox = new VBox(gridPane, scrollPane);
        vBox.setSpacing(10);
        BorderPane.setMargin(vBox, new Insets(10, 10, 0, 10));
        view.setCenter(vBox);
        return view;
    }

    private List<ExerciseResult> getExerciseResults() {
        List<ExerciseResult> exerciseResults = new ArrayList<>();
        for (Result result : results.values()) {
            exerciseResults.addAll(result.getResults());
        }
        return exerciseResults;
    }

    private List<AthleteAttendance> getAttendances() {
        List<AthleteAttendance> attendances = new ArrayList<>();
        for (Result result : results.values()) {
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
                TextField value = new TextField();
                TextField repeat = new TextField();
                TextField duration = new TextField();
                attendance.selectedProperty()
                        .addListener((observable, oldValue, newValue) -> {
                            value.setDisable(!newValue);
                            repeat.setDisable(!newValue);
                            duration.setDisable(!newValue);
                        });
                values.put(exercise, value);
                repeats.put(exercise, repeat);
                durations.put(exercise, duration);
            }
        }

        private TextField getField(TrainingExercise exercise, Map<TrainingExercise, TextField> fields) {
            TextField textField = fields.get(exercise);
//            if (textField == null) {
//                textField = new TextField();
//            }
            textField.setDisable(!attendance.isSelected());
            textField.setMaxWidth(65);
            return textField;
        }

        public List<ExerciseResult> getResults() {
            List<ExerciseResult> results = new ArrayList<>();
            if (attendance.isSelected()) {
                for (Map.Entry<TrainingExercise, TextField> entry : values.entrySet()) {
                    String repeatStr = repeats.get(entry.getKey()).getText();
                    int repeat = repeatStr.isEmpty() ? 0 : Integer.parseInt(repeatStr);
                    String durationStr = durations.get(entry.getKey()).getText();
                    int dur = durationStr.isEmpty() ? 0 : Integer.parseInt(durationStr);
                    Duration duration = Duration.ofMinutes(dur);
                    results.add(new ExerciseResult(athlete, entry.getKey(), repeat,
                            duration, entry.getValue().getText()));
                }
            }
            return results;
        }

        public void setAttendance(AthleteAttendance attendance) {
            if (athlete.equals(attendance.getAthlete())) {
                this.attendance.setSelected(attendance.getAttendance());
            }
        }

        public void setExerciseResult(ExerciseResult exerciseResult) {
            if (athlete.equals(exerciseResult.getAthlete())) {
                TextField valueField = getField(exerciseResult.getTrainingExercise(), values);
                if (valueField != null) {
                    valueField.setText(exerciseResult.getValue());
                }
                TextField repeatsField = getField(exerciseResult.getTrainingExercise(), repeats);
                if (repeatsField != null) {
                    repeatsField.setText(exerciseResult.getRepeat().toString());
                }
                TextField durationField = getField(exerciseResult.getTrainingExercise(), durations);
                if (durationField != null) {
                    durationField.setText(String.valueOf(exerciseResult.getDuration().toMinutes()));
                }
            }
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