package com.a31r.sport.coachassistant.core.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bahodurova on 1/7/2018.
 */
@Entity
@Table(name = "coaches")
public class Coach extends User {

    public Coach() {
    }

    public Coach(String name, String familyName, String patronymic) {
        super(name, familyName, patronymic);
    }

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL)
    private Set<TrainingSession> trainingSessions = new HashSet<>();

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL)
    private Set<TrainingGroup> trainingGroups = new HashSet<>();

    public Set<TrainingSession> getTrainingSessions() {
        return trainingSessions;
    }

    public void setTrainingSessions(Set<TrainingSession> trainingSessions) {
        this.trainingSessions = trainingSessions;
    }

    public Set<TrainingGroup> getTrainingGroups() {
        return trainingGroups;
    }

    public void setTrainingGroups(Set<TrainingGroup> trainingGroups) {
        this.trainingGroups = trainingGroups;
    }

    public void addSessions(TrainingSession session) {
        trainingSessions.add(session);
        if (session.getCoach() != this) {
            session.setCoach(this);
        }
    }

    public void removeSession(TrainingSession session) {
        trainingSessions.remove(session);
        session.setCoach(null);
    }

    public void addGroup(TrainingGroup group) {
        trainingGroups.add(group);
        if (group.getCoach() != this) {
            group.setCoach(this);
        }
    }

    public void removeGroup(TrainingGroup group) {
        trainingGroups.remove(group);
        group.setCoach(null);
    }
}