package com.a31r.sport.coachassistant.core.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by bahodurova on 1/12/2018.
 */
@Entity
@Table(name = "athlete_parameter_values")
public class AthleteParameterValue extends AbstractEntity {

    public AthleteParameterValue() {
    }

    public AthleteParameterValue(AthleteParameter parameter, LocalDate date, String value) {
        this.parameter = parameter;
        this.date = date;
        this.value = value;
    }

    public AthleteParameterValue(LocalDate date, String value) {
        this.date = date;
        this.value = value;
    }

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "parameter_id")
    private AthleteParameter parameter;

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

    public AthleteParameter getParameter() {
        return parameter;
    }

    public void setParameter(AthleteParameter parameter) {
        this.parameter = parameter;
        if (!parameter.getValues().contains(this))
        {
            parameter.getValues().add(this);
        }
    }
}