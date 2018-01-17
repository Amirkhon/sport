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

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "training_group_session",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id"))
    private Set<TrainingSession> sessions = new HashSet<>();

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
        if (!coach.getTrainingGroups().contains(this)) {
            coach.getTrainingGroups().add(this);
        }
    }

    public Set<TrainingSession> getSessions() {
        return sessions;
    }

    public void setSessions(Set<TrainingSession> sessions) {
        this.sessions = sessions;
    }

    public void addSession(TrainingSession session) {
        sessions.add(session);
        if (!session.getGroups().contains(this)) {
            session.getGroups().add(this);
        }
    }

    public void removeSession(TrainingSession session) {
        sessions.remove(session);
        session.getGroups().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TrainingGroup that = (TrainingGroup) o;

        return coach != null ? coach.equals(that.coach) : that.coach == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (coach != null ? coach.hashCode() : 0);
        return result;
    }
}