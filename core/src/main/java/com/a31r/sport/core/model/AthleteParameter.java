package com.a31r.sport.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bahodurova on 1/7/2018.
 */
@Entity
@Table(name = "athlete_parameters")
public class AthleteParameter extends AbstractEntity {

    public AthleteParameter() {
    }

    public AthleteParameter(Athlete athlete, AthleteParameterType parameterType) {
        this.athlete = athlete;
        this.parameterType = parameterType;
    }

    @ManyToOne
    @JoinColumn(name = "athlete_id", nullable = false)
    private Athlete athlete;

    @OneToOne
    @JoinColumn(name = "parameter_type_id", nullable = false)
    private AthleteParameterType parameterType;

    @OneToMany(mappedBy = "parameter")
    private List<AthleteParameterValue> values = new ArrayList<>();

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
        if (!athlete.getParameters().contains(this)) {
            athlete.getParameters().add(this);
        }
    }

    public AthleteParameterType getParameterType() {
        return parameterType;
    }

    public List<AthleteParameterValue> getValues() {
        return values;
    }

    public void setValues(List<AthleteParameterValue> values) {
        this.values = values;
    }

    public void setParameterType(AthleteParameterType parameterType) {
        this.parameterType = parameterType;
    }

    public void addValue(AthleteParameterValue value) {
        this.values.add(value);
        if (value.getParameter() != this) {
            value.setParameter(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AthleteParameter that = (AthleteParameter) o;

        if (athlete != null ? !athlete.equals(that.athlete) : that.athlete != null) return false;
        return parameterType != null ? parameterType.equals(that.parameterType) : that.parameterType == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (athlete != null ? athlete.hashCode() : 0);
        result = 31 * result + (parameterType != null ? parameterType.hashCode() : 0);
        return result;
    }
}