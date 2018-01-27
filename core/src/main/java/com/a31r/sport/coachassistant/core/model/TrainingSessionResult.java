package com.a31r.sport.coachassistant.core.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bahodurova on 1/21/2018.
 */
@Entity
@Table(name = "Training_session_result")
public class TrainingSessionResult extends AbstractEntity {

    public TrainingSessionResult() {
    }

    public TrainingSessionResult(TrainingSession trainingSession, LocalDate date) {
        this.trainingSession = trainingSession;
        this.date = date;
    }

    @ManyToOne
    private TrainingSession trainingSession;

    @Column
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AthleteAttendance> attendances = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseResult> results = new ArrayList<>();

    @OneToOne
    private Coach author;

    public TrainingSession getTrainingSession() {
        return trainingSession;
    }

    public void setTrainingSession(TrainingSession trainingSession) {
        this.trainingSession = trainingSession;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<AthleteAttendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<AthleteAttendance> attendances) {
        this.attendances = attendances;
    }

    public List<ExerciseResult> getResults() {
        return results;
    }

    public void setResults(List<ExerciseResult> results) {
        this.results = results;
    }

    public Coach getAuthor() {
        return author;
    }

    public void setAuthor(Coach author) {
        this.author = author;
    }

    public void addAttendance(AthleteAttendance attendance) {
        this.attendances.add(attendance);
        attendance.setTrainingSessionResult(this);
    }

    public void removeAttendance(AthleteAttendance attendance) {
        this.attendances.remove(attendance);
        attendance.setTrainingSessionResult(null);
    }

    public void addResult(ExerciseResult result) {
        this.results.add(result);
        result.setTrainingSessionResult(this);
    }

    public void removeResult(ExerciseResult result) {
        this.results.remove(result);
        result.setTrainingSessionResult(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TrainingSessionResult that = (TrainingSessionResult) o;

        if (trainingSession != null ? !trainingSession.equals(that.trainingSession) : that.trainingSession != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return author != null ? author.equals(that.author) : that.author == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (trainingSession != null ? trainingSession.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }
}
