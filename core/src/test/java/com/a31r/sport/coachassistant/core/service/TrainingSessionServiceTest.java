package com.a31r.sport.coachassistant.core.service;

import com.a31r.sport.coachassistant.core.model.Exercise;
import com.a31r.sport.coachassistant.core.model.TrainingExercise;
import com.a31r.sport.coachassistant.core.model.TrainingGroup;
import com.a31r.sport.coachassistant.core.model.TrainingSession;
import com.a31r.sport.coachassistant.core.model.repository.ExerciseRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingExerciseRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingGroupRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingSessionRepository;

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

    private static boolean setupIsDone;
    private static long sessionId;
    private static long exerciseId;
    private static long groupId;

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
        // Training Session
        TrainingSession session = new TrainingSession();
        session.addTrainingExercise(trainingExercise);
        session.addGroup(group);
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
        // Training Session
        TrainingSession session = new TrainingSession();
        session.addTrainingExercise(trainingExercise);
        session.addGroup(group);
        session = service.save(session);
        assertFalse(session.getId() == 0);
        List<TrainingExercise> exercises = trainingExerciseRepository.findAllByTrainingSession(session);
        assertFalse(exercises.isEmpty());
        assertEquals(exerciseId, exercises.get(0).getExercise().getId());
        List<TrainingGroup> groups = groupRepository.findAllBySessionsContains(session);
        assertFalse(groups.isEmpty());
        assertEquals(groupId, groups.get(0).getId());
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
        // Training Session
        TrainingSession session = new TrainingSession();
        session.addTrainingExercise(trainingExercise);
        session.addGroup(group);
        session = repository.save(session);
        service.delete(session);
        assertTrue(trainingExerciseRepository.findAllByTrainingSession(session).isEmpty());
        assertTrue(groupRepository.findAllBySessionsContains(session).isEmpty());
    }
}
