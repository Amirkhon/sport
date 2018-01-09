package com.a31r.sport.core.model;

import javax.persistence.*;
import java.time.Duration;

/**
 * Created by bahodurova on 1/7/2018.
 */
@Entity
@Table(name = "exercise_results")
public class ExerciseResult extends AbstractEntity {

    public ExerciseResult() {
    }

    public ExerciseResult(Athlete athlete, TrainingExercise trainingExercise, Integer repeat, Duration duration, String value) {
        this.athlete = athlete;
        this.trainingExercise = trainingExercise;
        this.repeat = repeat;
        this.duration = duration;
        this.value = value;
    }

    @ManyToOne
    @JoinColumn(name = "athlete_id", nullable = false)
    private Athlete athlete;

    @ManyToOne
    @JoinColumn(name = "training_exercise_id", nullable = false)
    private TrainingExercise trainingExercise;

    @Column(name = "repeat")
    private Integer repeat;

    @Column(name = "duration")
    private Duration duration;

    @Column(name = "value")
    private String value;

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public TrainingExercise getTrainingExercise() {
        return trainingExercise;
    }

    public void setTrainingExercise(TrainingExercise trainingExercise) {
        this.trainingExercise = trainingExercise;
    }

    public Integer getRepeat() {
        return repeat;
    }

    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ExerciseResult that = (ExerciseResult) o;

        if (athlete != null ? !athlete.equals(that.athlete) : that.athlete != null) return false;
        if (trainingExercise != null ? !trainingExercise.equals(that.trainingExercise) : that.trainingExercise != null)
            return false;
        if (repeat != null ? !repeat.equals(that.repeat) : that.repeat != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (athlete != null ? athlete.hashCode() : 0);
        result = 31 * result + (trainingExercise != null ? trainingExercise.hashCode() : 0);
        result = 31 * result + (repeat != null ? repeat.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}