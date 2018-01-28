package com.a31r.sport.coachassistant.core.model;

import javax.persistence.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bahodurova on 1/7/2018.
 */
@Entity
@Table(name = "training_exercise")
public class TrainingExercise extends AbstractEntity {

    public TrainingExercise() {
    }

    public TrainingExercise(Integer sequenceNumber, Integer repetitions, Duration duration, Exercise exercise, TrainingSession trainingSession) {
        this.sequenceNumber = sequenceNumber;
        this.repetitions = repetitions;
        this.duration = duration;
        this.exercise = exercise;
        this.trainingSession = trainingSession;
    }

    @Column(name = "sequence_number")
    private Integer sequenceNumber;

    @Column(name = "repetitions")
    private Integer repetitions;

    @Column(name = "duration")
    private Duration duration;

    @OneToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "training_session_id")
    private TrainingSession trainingSession;

    @OneToMany(mappedBy = "trainingExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseResult> results = new ArrayList<>();

    @ManyToMany(mappedBy = "members", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<TrainingExerciseGroup> groups = new HashSet<>();

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public TrainingSession getTrainingSession() {
        return trainingSession;
    }

    public void setTrainingSession(TrainingSession trainingSession) {
        this.trainingSession = trainingSession;
    }

    public List<ExerciseResult> getResults() {
        return results;
    }

    public void setResults(List<ExerciseResult> results) {
        this.results = results;
    }

    public Set<TrainingExerciseGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<TrainingExerciseGroup> groups) {
        this.groups = groups;
    }

    public void addResult(ExerciseResult result) {
        results.add(result);
    }

    public void removeResult(ExerciseResult result) {
        results.remove(result);
    }

    public void addGroup(TrainingExerciseGroup group) {
        this.groups.add(group);
    }

    public void removeGroup(TrainingExerciseGroup group) {
        this.groups.remove(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TrainingExercise that = (TrainingExercise) o;

        if (sequenceNumber != null ? !sequenceNumber.equals(that.sequenceNumber) : that.sequenceNumber != null)
            return false;
        if (repetitions != null ? !repetitions.equals(that.repetitions) : that.repetitions != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        return exercise != null ? exercise.equals(that.exercise) : that.exercise == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sequenceNumber != null ? sequenceNumber.hashCode() : 0);
        result = 31 * result + (repetitions != null ? repetitions.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (exercise != null ? exercise.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s - %d раза (%d мин.)", exercise, repetitions, duration.toMinutes());
    }
}