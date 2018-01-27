package com.a31r.sport.coachassistant.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bahodurova on 1/7/2018.
 */
@Entity
@Table(name = "athletes")
public class Athlete extends User {

    public Athlete() {
    }

    public Athlete(String name, String familyName, String patronymic) {
        super(name, familyName, patronymic);
    }

    @OneToMany(mappedBy = "athlete", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AthleteParameter> parameters = new ArrayList<>();

    @OneToMany(mappedBy = "athlete", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ExerciseResult> results = new ArrayList<>();

    @OneToMany(mappedBy = "athlete", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AthleteAttendance> attendances = new ArrayList<>();

    public List<AthleteParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<AthleteParameter> parameters) {
        this.parameters = parameters;
    }

    public List<ExerciseResult> getResults() {
        return results;
    }

    public void setResults(List<ExerciseResult> results) {
        this.results = results;
    }

    public List<AthleteAttendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<AthleteAttendance> attendances) {
        this.attendances = attendances;
    }

    public void addParameter(AthleteParameter parameter) {
        parameters.add(parameter);
        parameter.setAthlete(this);
    }

    public void removeParameter(AthleteParameter parameter) {
        parameters.remove(parameter);
        parameter.setAthlete(null);
    }

    public void addResult(ExerciseResult result) {
        results.add(result);
        result.setAthlete(this);
    }

    public void removeResult(ExerciseResult result) {
        results.remove(result);
        result.setAthlete(null);
    }

    public void addAttendance(AthleteAttendance athleteAttendance) {
        attendances.add(athleteAttendance);
        athleteAttendance.setAthlete(this);
    }

    public void removeAttendance(AthleteAttendance athleteAttendance) {
        attendances.remove(athleteAttendance);
        athleteAttendance.setAthlete(null);
    }

    public String shortFullName() {
        return getFamilyName() + " " + getName().charAt(0) + "." + getPatronymic().charAt(0) + "." + getBirthdayString();
    }

    @Override
    public String toString() {
        return super.toString() + getBirthdayString();
    }
}