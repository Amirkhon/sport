package com.a31r.sport.coachassistant.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bahodurova on 1/7/2018.
 */
@Entity
@Table(name = "exercises")
public class Exercise extends AbstractEntity {

    public Exercise() {
    }

    public Exercise(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "unit", nullable = false)
    private String unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, unit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Exercise exercise = (Exercise) o;

        if (name != null ? !name.equals(exercise.name) : exercise.name != null) return false;
        return unit != null ? unit.equals(exercise.unit) : exercise.unit == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }
}