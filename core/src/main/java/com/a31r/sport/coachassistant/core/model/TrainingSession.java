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

    public TrainingSession(String name) {
        this.name = name;
    }

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "schedule")
    private Schedule schedule;

    @OneToMany(mappedBy = "trainingSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingExercise> exercises = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "training_session_coach",
            joinColumns = @JoinColumn(name = "training_session_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "coach_id", referencedColumnName = "id"))
    private Set<Coach> coaches = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "training_group_session",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id"))
    private Set<TrainingGroup> groups = new HashSet<>();

    @OneToMany(mappedBy = "trainingSession", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Set<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(Set<Coach> coaches) {
        this.coaches = coaches;
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
        exercise.setTrainingSession(this);
    }

    public void removeTrainingExercise(TrainingExercise exercise) {
        exercises.remove(exercise);
        exercise.setTrainingSession(null);
    }

    public void addCoach(Coach coach) {
        coaches.add(coach);
    }

    public void removeCoach(Coach coach) {
        coaches.remove(coach);
    }

    public void addGroup(TrainingGroup group) {
        groups.add(group);
    }

    public void removeGroup(TrainingGroup group) {
        groups.remove(group);
    }

    public void addResult(TrainingSessionResult result) {
        results.add(result);
        result.setTrainingSession(this);
    }

    public void removeResult(TrainingSessionResult result) {
        results.remove(result);
        result.setTrainingSession(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TrainingSession that = (TrainingSession) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}