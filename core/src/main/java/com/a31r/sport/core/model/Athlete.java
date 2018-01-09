package com.a31r.sport.core.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL)
    private List<AthleteParameter> parameters = new ArrayList<>();

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL)
    private List<ExerciseResult> results = new ArrayList<>();

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

}