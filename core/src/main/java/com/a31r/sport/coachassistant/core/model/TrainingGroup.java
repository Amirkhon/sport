package com.a31r.sport.coachassistant.core.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bahodurova on 1/7/2018.
 */
@Entity
@Table(name = "training_groups")
public class TrainingGroup extends UserGroup {

    public TrainingGroup() {
    }

    public TrainingGroup(String name) {
        super(name);
    }

    @ManyToMany
    @JoinTable(name = "training_group_coach",
            joinColumns = @JoinColumn(name = "training_group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "coach_id", referencedColumnName = "id"))
    private Set<Coach> coaches = new HashSet<>();

    @ManyToMany(mappedBy = "groups")
    private Set<TrainingSession> sessions = new HashSet<>();

    public Set<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(Set<Coach> coaches) {
        this.coaches = coaches;
    }

    public Set<TrainingSession> getSessions() {
        return sessions;
    }

    public void removeSession(TrainingSession session) {
        sessions.remove(session);
    }

    public void addCoach(Coach coach) {
        coaches.add(coach);
    }

    public void removeCoach(Coach coach) {
        coaches.remove(coach);
    }

}