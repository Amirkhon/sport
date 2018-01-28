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

    @ManyToMany(mappedBy = "coaches")
    private Set<TrainingSession> trainingSessions = new HashSet<>();

    @ManyToMany(mappedBy = "coaches")
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

}