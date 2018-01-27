package com.a31r.sport.coachassistant.core.service;

import com.a31r.sport.coachassistant.core.model.*;
import com.a31r.sport.coachassistant.core.model.repository.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by bahodurova on 1/27/2018.
 */
public class TrainingSessionResultServiceTest extends DataServiceTest<TrainingSessionResult> {

    @Resource
    private TrainingSessionResultService service;
    @Resource
    private TrainingSessionResultRepository repository;
    @Resource
    private AthleteRepository athleteRepository;
    @Resource
    private ExerciseRepository exerciseRepository;
    @Resource
    private TrainingSessionRepository sessionRepository;
    @Resource
    private AthleteAttendanceRepository attendanceRepository;
    @Resource
    private ExerciseResultRepository exerciseResultRepository;

    private static boolean setupIsDone;
    private static long sessionResultId;
    private static long athleteId;

    @Override
    protected DataService<TrainingSessionResult> getService() {
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
        return sessionResultId;
    }

    @Override
    protected void setup() {
        // Exercise
        Exercise exercise = new Exercise("Running", "sec");
        exercise = exerciseRepository.save(exercise);
        // TrainingExercise
        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setExercise(exercise);
        // Training Session
        TrainingSession session = new TrainingSession("TSName");
        session.addTrainingExercise(trainingExercise);
        session = sessionRepository.save(session);
        // Athlete
        Athlete athlete = new Athlete("AthleteN", "AthleteFN", "AthleteP");
        // Athlete Attendance
        AthleteAttendance attendance = new AthleteAttendance(athlete, false);
        athlete = athleteRepository.save(athlete);
        athleteId = athlete.getId();
        // Exercise Result
        ExerciseResult exerciseResult = new ExerciseResult();
        exerciseResult.setTrainingExercise(trainingExercise);
        exerciseResult.setAthlete(athlete);
        // Session Result
        TrainingSessionResult sessionResult = new TrainingSessionResult();
        sessionResult.addResult(exerciseResult);
        sessionResult.addAttendance(attendance);
        sessionResult.setTrainingSession(session);
        sessionResultId = repository.save(sessionResult).getId();
    }

    @Override
    public void testInitialize() {
        TrainingSessionResult sessionResult = repository.getOne(sessionResultId);
        sessionResult = service.initialize(sessionResult);
        assertNotNull(sessionResult.getAttendances());
        assertFalse(sessionResult.getAttendances().isEmpty());
        assertEquals(1, sessionResult.getAttendances().size());
        assertEquals(athleteId, sessionResult.getAttendances().get(0).getAthlete().getId());
        assertNotNull(sessionResult.getResults());
        assertFalse(sessionResult.getResults().isEmpty());
        assertEquals(1, sessionResult.getResults().size());
        assertEquals(athleteId, sessionResult.getResults().get(0).getAthlete().getId());
    }

    @Override
    public void testSave() {
        // Exercise
        Exercise exercise = new Exercise("Running", "sec");
        exercise = exerciseRepository.save(exercise);
        // TrainingExercise
        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setExercise(exercise);
        // Training Session
        TrainingSession session = new TrainingSession("TSName");
        session.addTrainingExercise(trainingExercise);
        session = sessionRepository.save(session);
        // Athlete
        Athlete athlete = new Athlete("AthleteN", "AthleteFN", "AthleteP");
        // Athlete Attendance
        AthleteAttendance attendance = new AthleteAttendance(athlete, false);
        athlete = athleteRepository.save(athlete);
        // Exercise Result
        ExerciseResult exerciseResult = new ExerciseResult();
        exerciseResult.setTrainingExercise(trainingExercise);
        exerciseResult.setAthlete(athlete);
        // Session Result
        TrainingSessionResult sessionResult = new TrainingSessionResult();
        sessionResult.addResult(exerciseResult);
        sessionResult.addAttendance(attendance);
        sessionResult.setTrainingSession(session);
        sessionResult = service.save(sessionResult);
        assertFalse(repository.findAllByTrainingSession(session).isEmpty());
        List<AthleteAttendance> attendances =  attendanceRepository.findAllByTrainingSessionResult(sessionResult);
        assertFalse(attendances.isEmpty());
        assertEquals(athlete.getId(), attendances.get(0).getAthlete().getId());
        List<ExerciseResult> results = exerciseResultRepository.findAllByTrainingSessionResult(sessionResult);
        assertFalse(results.isEmpty());
        assertEquals(athlete.getId(), results.get(0).getAthlete().getId());
    }

    @Override
    public void testDelete() {
        // Exercise
        Exercise exercise = new Exercise("Running", "sec");
        exercise = exerciseRepository.save(exercise);
        // TrainingExercise
        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setExercise(exercise);
        // Training Session
        TrainingSession session = new TrainingSession("TSName");
        session.addTrainingExercise(trainingExercise);
        session = sessionRepository.save(session);
        // Athlete
        Athlete athlete = new Athlete("AthleteN", "AthleteFN", "AthleteP");
        // Athlete Attendance
        AthleteAttendance attendance = new AthleteAttendance(athlete, false);
        athlete = athleteRepository.save(athlete);
        // Exercise Result
        ExerciseResult exerciseResult = new ExerciseResult();
        exerciseResult.setTrainingExercise(trainingExercise);
        exerciseResult.setAthlete(athlete);
        // Session Result
        TrainingSessionResult sessionResult = new TrainingSessionResult();
        sessionResult.addResult(exerciseResult);
        sessionResult.addAttendance(attendance);
        sessionResult.setTrainingSession(session);
        sessionResult = repository.save(sessionResult);

        service.delete(sessionResult);

        assertTrue(repository.findAllByTrainingSession(session).isEmpty());
        List<AthleteAttendance> attendances =  attendanceRepository.findAllByTrainingSessionResult(sessionResult);
        assertTrue(attendances.isEmpty());
        List<ExerciseResult> results = exerciseResultRepository.findAllByTrainingSessionResult(sessionResult);
        assertTrue(results.isEmpty());
    }
}
