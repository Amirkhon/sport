package com.a31r.sport.coachassistant.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bahodurova on 1/8/2018.
 */
@Entity
@Table(name = "training_sessions")
public class TrainingSession extends AbstractEntity {

    public TrainingSession() {
    }

    public TrainingSession(Schedule schedule) {
        this.schedule = schedule;
    }

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "schedule")
    private Schedule schedule;

    @OneToMany(mappedBy = "trainingSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingExercise> exercises = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToMany(mappedBy = "sessions")
    private Set<TrainingGroup> groups = new HashSet<>();

    @OneToMany(mappedBy = "trainingSession", orphanRemoval = true)
    private List<TrainingSessionResult> results = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<TrainingExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<TrainingExercise> exercises) {
        this.exercises = exercises;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Set<TrainingGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<TrainingGroup> groups) {
        this.groups = groups;
    }

    public List<TrainingSessionResult> getResults() {
        return results;
    }

    public void setResults(List<TrainingSessionResult> results) {
        this.results = results;
    }

    public void addTrainingExercise(TrainingExercise exercise) {
        exercises.add(exercise);
    }

    public void removeTrainingExercise(TrainingExercise exercise) {
        exercises.remove(exercise);
    }

    public void addGroup(TrainingGroup group) {
        groups.add(group);
    }

    public void removeGroup(TrainingGroup group) {
        groups.remove(group);
    }

    public void addResult(TrainingSessionResult result) {
        this.results.add(result);
    }

    public void removeResult(TrainingSessionResult result) {
        this.results.remove(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TrainingSession that = (TrainingSession) o;

        if (schedule != null ? !schedule.equals(that.schedule) : that.schedule != null) return false;
        return coach != null ? coach.equals(that.coach) : that.coach == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (schedule != null ? schedule.hashCode() : 0);
        result = 31 * result + (coach != null ? coach.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, coach);
    }
}