package com.a31r.sport.coachassistant.desktop.model.editor;

import com.a31r.sport.coachassistant.core.model.Exercise;
import com.a31r.sport.coachassistant.core.model.TrainingExercise;
import com.a31r.sport.coachassistant.core.model.service.DataService;
import com.a31r.sport.coachassistant.core.model.service.ExerciseService;
import com.a31r.sport.coachassistant.core.model.service.TrainingExerciseService;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Created by bahodurova on 1/21/2018.
 */
@Component
public class TrainingExerciseEditor extends AbstractEditor<TrainingExercise> {

    @Autowired
    private TrainingExerciseService service;
    @Autowired
    private ExerciseService exerciseService;


    private ComboBox<Exercise> exerciseComboBox = new ComboBox<>();
    private TextField trainingSession = new TextField();
    private TextField sequenceNumber = new TextField();
    private TextField repetitions = new TextField();
    private TextField duration = new TextField();

    public TrainingExerciseEditor() {
        gridPane.add(new Label("Тренировка"), 0,0);
        gridPane.add(trainingSession, 1,0);
        gridPane.add(new Label("Упражнение"), 0,1);
        gridPane.add(exerciseComboBox, 1,1);
        gridPane.add(new Label("Порядковый номер"), 0,2);
        gridPane.add(sequenceNumber, 1,2);
        gridPane.add(new Label("К-во повторений"), 0,3);
        gridPane.add(repetitions, 1,3);
        gridPane.add(new Label("Продолжительность (мин)"), 0,4);
        gridPane.add(duration, 1,4);
        trainingSession.setDisable(true);
        sequenceNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                sequenceNumber.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        repetitions.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                sequenceNumber.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        duration.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                sequenceNumber.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @Override
    public TrainingExercise newObject() {
        return new TrainingExercise();
    }

    @Override
    protected DataService<TrainingExercise> getService() {
        return service;
    }

    @Override
    protected void beforeSave() {
        object.setExercise(exerciseComboBox.getValue());
        object.setSequenceNumber(Integer.parseInt(sequenceNumber.getText()));
        object.setRepetitions(Integer.parseInt(repetitions.getText()));
        object.setDuration(Duration.ofMinutes(Integer.parseInt(duration.getText())));
    }

    @Override
    protected void setData() {
        exerciseComboBox.getSelectionModel().select(object.getExercise());
        sequenceNumber.setText(String.valueOf(object.getSequenceNumber()));
        repetitions.setText(String.valueOf(object.getRepetitions()));
        duration.setText(String.valueOf(object.getDuration() == null ? 0 : object.getDuration().toMinutes()));
        trainingSession.setText(object.getTrainingSession().getName());
    }

    @Override
    protected void setDisabled(boolean disabled) {
        exerciseComboBox.setDisable(disabled);
        sequenceNumber.setDisable(disabled);
        repetitions.setDisable(disabled);
        duration.setDisable(disabled);
    }

    @Override
    protected void beforeShow() {
        super.beforeShow();
        exerciseComboBox.setItems(FXCollections.observableArrayList(exerciseService.findAll()));
    }
}