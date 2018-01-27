package com.a31r.sport.coachassistant.core.service;

import com.a31r.sport.coachassistant.core.model.*;
import com.a31r.sport.coachassistant.core.model.repository.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by bahodurova on 1/27/2018.
 */
public class AthleteServiceTest extends DataServiceTest<Athlete> {

    @Resource
    private AthleteService service;
    @Resource
    private AthleteRepository repository;
    @Resource
    private TrainingGroupRepository groupRepository;
    @Resource
    private AthleteParameterTypeRepository typeRepository;
    @Resource
    private AthleteParameterRepository parameterRepository;
    @Resource
    private ExerciseRepository exerciseRepository;
    @Resource
    private TrainingExerciseRepository trainingExerciseRepository;
    @Resource
    private ExerciseResultRepository resultRepository;

    private static boolean setupIsDone = false;
    private static long athleteId;
    private static long parameterTypeId;
    private static long groupId;
    private static long trainingExerciseId;

    @Override
    protected DataService<Athlete> getService() {
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
    public long getId() {
        return athleteId;
    }

    @Override
    public void setup() {
        // Parameter Type
        AthleteParameterType parameterType = new AthleteParameterType("Wight", "kg");
        parameterType = typeRepository.save(parameterType);
        parameterTypeId = parameterType.getId();
        // Parameter
        AthleteParameterValue parameterValue = new AthleteParameterValue(LocalDate.now(), "60");
        AthleteParameter parameter = new AthleteParameter(parameterType, parameterValue);
        // Exercise
        Exercise exercise = new Exercise("GYM", "number");
        exercise = exerciseRepository.save(exercise);
        // Training Exercise
        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setExercise(exercise);
        trainingExercise = trainingExerciseRepository.save(trainingExercise);
        trainingExerciseId = trainingExercise.getId();
        // Exercise Result
        ExerciseResult result = new ExerciseResult();
        result.setTrainingExercise(trainingExercise);
        // Athlete Attendance
        AthleteAttendance attendance = new AthleteAttendance();
        // Athlete
        Athlete athlete = new Athlete("AthleteN", "AthleteFN", "AthleteP");
        athlete.addParameter(parameter);
        athlete.addResult(result);
        athlete.addAttendance(attendance);
        athlete = repository.save(athlete);
        athleteId = athlete.getId();
        // Training Group
        TrainingGroup group = new TrainingGroup("TGName");
        group.addMember(athlete);
        groupId = groupRepository.save(group).getId();
    }

    @Override
    public void testInitialize() {
        Athlete athlete = repository.getOne(athleteId);
        athlete = service.initialize(athlete);
        assertNotNull(athlete.getParameters());
        assertFalse(athlete.getParameters().isEmpty());
        assertEquals(1, athlete.getParameters().size());
        assertEquals(parameterTypeId, athlete.getParameters().get(0).getParameterType().getId());
        assertNotNull(athlete.getGroups());
        assertFalse(athlete.getGroups().isEmpty());
        assertEquals(1, athlete.getGroups().size());
        assertEquals(groupId, athlete.getGroups().iterator().next().getId());
        assertNotNull(athlete.getResults());
        assertFalse(athlete.getResults().isEmpty());
        assertEquals(1, athlete.getResults().size());
        assertEquals(trainingExerciseId, athlete.getResults().get(0).getTrainingExercise().getId());
        assertNotNull(athlete.getAttendances());
        assertFalse(athlete.getAttendances().isEmpty());
        assertEquals(1, athlete.getAttendances().size());
    }

    @Override
    public void testSave() {
        Athlete athlete = new Athlete("NewAthleteN", "NewAthleteFN", "NewAthleteP");
        AthleteParameterType parameterType = new AthleteParameterType("Height", "cm");
        parameterType = typeRepository.save(parameterType);
        long parameterTypeId = parameterType.getId();
        AthleteParameterValue parameterValue = new AthleteParameterValue(LocalDate.now(), "60");
        AthleteParameter parameter = new AthleteParameter(parameterType, parameterValue);
        athlete.addParameter(parameter);
        athlete = service.save(athlete);
        assertFalse(athlete.getId() == 0);
        List<AthleteParameter> parameters = parameterRepository.findAllByAthlete(athlete);
        assertFalse(parameters.isEmpty());
        for (AthleteParameter p : parameters) {
            if (p.getParameterType().getId() == parameterTypeId) {
                assertFalse(p.getValues().isEmpty());
                assertEquals("60", p.getValues().get(0).getValue());
            }
        }
    }

    @Override
    public void testDelete() {
        Athlete athlete = new Athlete("NewAthleteN", "NewAthleteFN", "NewAthleteP");
        athlete = repository.save(athlete);
        service.delete(athlete);
        assertFalse(repository.existsById(athlete.getId()));
        assertTrue(groupRepository.findAllByMembersContains(athlete).isEmpty());
        assertTrue(resultRepository.findAllByAthlete(athlete).isEmpty());
    }
}