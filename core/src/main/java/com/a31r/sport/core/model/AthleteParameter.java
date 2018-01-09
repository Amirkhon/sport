package com.a31r.sport.core.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by bahodurova on 1/7/2018.
 */
@Entity
@Table(name = "athlete_parameters")
public class AthleteParameter extends AbstractEntity {

    public AthleteParameter() {
    }

    public AthleteParameter(Athlete athlete, AthleteParameterType parameter, LocalDate date, String value) {
        this.athlete = athlete;
        this.parameter = parameter;
        this.date = date;
        this.value = value;
    }

    @ManyToOne
    @JoinColumn(name = "athlete_id", nullable = false)
    private Athlete athlete;

    @OneToOne
    @JoinColumn(name = "parameter_id", nullable = false)
    private AthleteParameterType parameter;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "value", nullable = false)
    private String value;

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public AthleteParameterType getParameter() {
        return parameter;
    }

    public void setParameter(AthleteParameterType parameter) {
        this.parameter = parameter;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AthleteParameter that = (AthleteParameter) o;

        if (athlete != null ? !athlete.equals(that.athlete) : that.athlete != null) return false;
        if (parameter != null ? !parameter.equals(that.parameter) : that.parameter != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (athlete != null ? athlete.hashCode() : 0);
        result = 31 * result + (parameter != null ? parameter.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}