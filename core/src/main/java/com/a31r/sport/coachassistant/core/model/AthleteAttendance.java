package com.a31r.sport.coachassistant.core.model;

import javax.persistence.*;

/**
 * Created by bahodurova on 1/21/2018.
 */
@Entity
@Table(name = "athlete_attendance")
public class AthleteAttendance extends AbstractEntity {

    public AthleteAttendance() {
    }

    public AthleteAttendance(Athlete athlete, TrainingSessionResult trainingSessionResult) {
        this.athlete = athlete;
        this.trainingSessionResult = trainingSessionResult;
    }

    public AthleteAttendance(Athlete athlete, TrainingSessionResult trainingSessionResult, Boolean attendance) {
        this.athlete = athlete;
        this.trainingSessionResult = trainingSessionResult;
        this.attendance = attendance;
    }

    @OneToOne
    private Athlete athlete;

    @ManyToOne
    private TrainingSessionResult trainingSessionResult;

    @Column
    private Boolean attendance;

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public TrainingSessionResult getTrainingSessionResult() {
        return trainingSessionResult;
    }

    public void setTrainingSessionResult(TrainingSessionResult trainingSessionResult) {
        this.trainingSessionResult = trainingSessionResult;
    }

    public Boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AthleteAttendance that = (AthleteAttendance) o;

        if (athlete != null ? !athlete.equals(that.athlete) : that.athlete != null) return false;
        if (trainingSessionResult != null ? !trainingSessionResult.equals(that.trainingSessionResult) : that.trainingSessionResult != null)
            return false;
        return attendance != null ? attendance.equals(that.attendance) : that.attendance == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (athlete != null ? athlete.hashCode() : 0);
        result = 31 * result + (trainingSessionResult != null ? trainingSessionResult.hashCode() : 0);
        result = 31 * result + (attendance != null ? attendance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return athlete + (attendance ? "Был" : "Не был");
    }
}
