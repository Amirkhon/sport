package com.a31r.sport.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bahodurova on 1/8/2018.
 */
@Entity
@Table(name = "exercise_groups")
public class TrainingExerciseGroup extends AbstractEntity implements Group<TrainingExercise> {

    public TrainingExerciseGroup() {
    }

    public TrainingExerciseGroup(String name, Set<TrainingExercise> members) {
        this.name = name;
        this.members = members;
    }

    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinTable(name = "exercise_group_exercise",
            joinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "exercise_id", referencedColumnName = "id")})
    private Set<TrainingExercise> members = new HashSet<>();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<TrainingExercise> getMembers() {
        return members;
    }

    public void setMembers(Set<TrainingExercise> members) {
        this.members = members;
    }

    public void addMember(TrainingExercise exercise) {
        members.add(exercise);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TrainingExerciseGroup that = (TrainingExerciseGroup) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}