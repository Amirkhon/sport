package com.a31r.sport.coachassistant.core.model;

import javax.persistence.*;
import javax.transaction.Transactional;
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

    @OneToMany(mappedBy = "athlete", orphanRemoval = true)
    private List<AthleteParameter> parameters = new ArrayList<>();

    @OneToMany(mappedBy = "athlete", orphanRemoval = true)
    private List<ExerciseResult> results = new ArrayList<>();

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

    public void addParameter(AthleteParameter parameter) {
        parameters.add(parameter);
    }

    public void removeParameter(AthleteParameter parameter) {
        parameters.remove(parameter);
    }

    public void addResult(ExerciseResult result) {
        results.add(result);
    }

    public void removeResult(ExerciseResult result) {
        results.remove(result);
    }

    public String shortFullName() {
        return getFamilyName() + " " + getName().charAt(0) + "." + getPatronymic().charAt(0) + "." + getBirthdayString();
    }

    @Override
    public String toString() {
        return super.toString() + getBirthdayString();
    }
}