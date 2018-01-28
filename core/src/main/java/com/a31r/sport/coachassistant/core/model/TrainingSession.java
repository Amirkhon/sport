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

    @Lob
    @Column(name="CONTENT", length=512)
    private String event;

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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
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

    private String getWeekdays() {
        if (event != null) {
            List<String> days = new ArrayList<>();
            String eventString = event.toUpperCase();
            if (eventString.contains("MO")) {
                days.add("Пн");
            }
            if (eventString.contains("TU")) {
                days.add("Вт");
            }
            if (eventString.contains("WE")) {
                days.add("Ср");
            }
            if (eventString.contains("TH")) {
                days.add("Чт");
            }
            if (eventString.contains("FR")) {
                days.add("Пт");
            }
            if (eventString.contains("SA")) {
                days.add("Сб");
            }
            if (eventString.contains("SU")) {
                days.add("Вс");
            }
            StringBuilder sb = new StringBuilder("(");
            for (int i = 0; days.size() - 2 >  i; i++) {
                sb.append(days.get(i));
                sb.append(", ");
            }
            sb.append(days.get(days.size() - 1));
            sb.append(")");
            return sb.toString();
        }
        return "";
    }

    @Override
    public String toString() {
        return name + getWeekdays();
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