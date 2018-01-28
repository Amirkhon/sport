package com.a31r.sport.coachassistant.core.service;

import com.a31r.sport.coachassistant.core.model.*;
import com.a31r.sport.coachassistant.core.model.repository.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by bahodurova on 1/27/2018.
 */
public class TrainingSessionServiceTest extends DataServiceTest<TrainingSession> {

    @Resource
    private TrainingSessionService service;
    @Resource
    private TrainingSessionRepository repository;
    @Resource
    private ExerciseRepository exerciseRepository;
    @Resource
    private TrainingExerciseRepository trainingExerciseRepository;
    @Resource
    private TrainingGroupRepository groupRepository;
    @Resource
    private CoachRepository coachRepository;

    private static boolean setupIsDone;
    private static long sessionId;
    private static long exerciseId;
    private static long groupId;
    private static long coachId;

    @Override
    protected DataService<TrainingSession> getService() {
        return service;
    }

    @Override
    protected boolean getDone() {
        return setupIsDone;
    }

    @Override
    protected void setDone(boolean done) {
        setupIsDone = done;
    }

    @Override
    protected long getId() {
        return sessionId;
    }

    @Override
    protected void setup() {
        // Exercise
        Exercise exercise = new Exercise("Running", "sec");
        exercise = exerciseRepository.save(exercise);
        exerciseId = exercise.getId();
        // Training Exercise
        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setExercise(exercise);
        // Training Group
        TrainingGroup group = new TrainingGroup("TGName");
        group = groupRepository.save(group);
        groupId = group.getId();
        // Coach
        Coach coach = new Coach("CoachN", "CoachFN", "CoachP");
        coach = coachRepository.save(coach);
        coachId = coach.getId();
        // Training Session
        TrainingSession session = new TrainingSession();
        session.addTrainingExercise(trainingExercise);
        session.addGroup(group);
        session.addCoach(coach);
        sessionId = repository.save(session).getId();
    }

    @Override
    public void testInitialize() {
        TrainingSession session = repository.getOne(sessionId);
        session = service.initialize(session);
        assertNotNull(session.getExercises());
        assertFalse(session.getExercises().isEmpty());
        assertEquals(1, session.getExercises().size());
        assertEquals(exerciseId, session.getExercises().get(0).getExercise().getId());
        assertNotNull(session.getGroups());
        assertFalse(session.getGroups().isEmpty());
        assertEquals(1, session.getGroups().size());
        assertEquals(groupId, session.getGroups().iterator().next().getId());
        assertNotNull(session.getCoaches());
        assertFalse(session.getCoaches().isEmpty());
        assertEquals(1, session.getCoaches().size());
        assertEquals(coachId, session.getCoaches().iterator().next().getId());
    }

    @Override
    public void testSave() {
        // Exercise
        Exercise exercise = new Exercise("Running", "sec");
        exercise = exerciseRepository.save(exercise);
        long exerciseId = exercise.getId();
        // Training Exercise
        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setExercise(exercise);
        // Training Group
        TrainingGroup group = new TrainingGroup("TGName");
        group = groupRepository.save(group);
        long groupId = group.getId();
        // Coach
        Coach coach = new Coach("NewCoachN", "NewCoachFN", "NewCoachP");
        coach = coachRepository.save(coach);
        long coachId = coach.getId();
        // Training Session
        TrainingSession session = new TrainingSession();
        session.addTrainingExercise(trainingExercise);
        session.addGroup(group);
        session.addCoach(coach);
        session = service.save(session);
        assertFalse(session.getId() == 0);
        List<TrainingExercise> exercises = trainingExerciseRepository.findAllByTrainingSession(session);
        assertFalse(exercises.isEmpty());
        assertEquals(exerciseId, exercises.get(0).getExercise().getId());
        List<TrainingGroup> groups = groupRepository.findAllBySessionsContains(session);
        assertFalse(groups.isEmpty());
        assertEquals(groupId, groups.get(0).getId());
        List<Coach> coaches = coachRepository.findAllByTrainingSessionsContains(session);
        assertFalse(coaches.isEmpty());
        assertEquals(coachId, coaches.get(0).getId());
    }

    @Override
    public void testDelete() {
        // Exercise
        Exercise exercise = new Exercise("Running", "sec");
        exercise = exerciseRepository.save(exercise);
        // Training Exercise
        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setExercise(exercise);
        // Training Group
        TrainingGroup group = new TrainingGroup("TGName");
        group = groupRepository.save(group);
        // Coach
        Coach coach = new Coach("NewCoachN", "NewCoachFN", "NewCoachP");
        coach = coachRepository.save(coach);
        // Training Session
        TrainingSession session = new TrainingSession();
        session.addTrainingExercise(trainingExercise);
        session.addGroup(group);
        session.addCoach(coach);
        session = repository.save(session);
        service.delete(session);
        assertTrue(trainingExerciseRepository.findAllByTrainingSession(session).isEmpty());
        assertTrue(groupRepository.findAllBySessionsContains(session).isEmpty());
        assertTrue(coachRepository.findAllByTrainingSessionsContains(session).isEmpty());
    }
}
