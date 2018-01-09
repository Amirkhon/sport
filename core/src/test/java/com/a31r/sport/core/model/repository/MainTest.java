package com.a31r.sport.core.model.repository;

import com.a31r.sport.core.JpaConfig;
import com.a31r.sport.core.model.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bahodurova on 1/7/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { JpaConfig.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class MainTest extends TestCase{

    @Resource
    private AthleteRepository athleteRepository;

    @Resource
    private AthleteParameterRepository athleteParameterRepository;

    @Resource
    private AthleteParameterTypeRepository athleteParameterTypeRepository;

    @Resource
    private CoachRepository coachRepository;

    @Resource
    private ExerciseRepository exerciseRepository;

    @Resource
    private ExerciseResultRepository exerciseResultRepository;

    @Resource
    private ScheduleRepository scheduleRepository;

    @Resource
    private TrainingExerciseRepository trainingExerciseRepository;

    @Resource
    private TrainingExerciseGroupRepository trainingExerciseGroupRepository;

    @Resource
    private TrainingGroupRepository trainingGroupRepository;

    @Resource
    private TrainingSessionRepository trainingSessionRepository;

    @Resource
    private UserGroupRepository userGroupRepository;

    @Resource
    private UserPropertyRepository userPropertyRepository;

    @Resource
    private UserPropertyTypeRepository userPropertyTypeRepository;

    @Resource
    private UserRepository userRepository;

    private Long userId = 1L;
    private Long athleteId = 1L;
    private Long athleteParameterTypeId = 1L;
    private Long athleteParameterId = 1L;
    private Long coachId = 1L;
    private Long exerciseId = 1L;
    private Long exerciseResultId = 1L;
    private Long userGroupId = 1L;
    private Long scheduleId = 1L;
    private Long trainingExerciseId = 1L;
    private Long trainingGroupId = 1L;
    private Long trainingSessionId = 1L;
    private Long userPropertyTypeId = 1L;
    private Long userPropertyId = 1L;
    private final String username = "username";
    private final String athleteName = "athlete";
    private final String athleteParamName = "param";
    private final String athleteParamValue = "value";
    private final String coachName = "coach";
    private final String exerciseName = "exercise";
    private final String userGroupName = "userGroup";
    private final String trainingGroupName = "trainingGroup";
    private final String userPropertyName = "prop";
    private final String userPropertyValue = "value";
    private boolean setupIsDone = false;

    @Before
    public void setup() {
        if (setupIsDone) {
            return;
        }

        User user = new User();
        user.setUsername(username);
        userId = userRepository.save(user).getId();

        Athlete athlete = new Athlete();
        athlete.setName(athleteName);
        athleteId = athleteRepository.save(athlete).getId();

        AthleteParameterType athleteParameterType = new AthleteParameterType(athleteParamName, "unit");
        athleteParameterTypeId = athleteParameterTypeRepository.save(athleteParameterType).getId();

        AthleteParameter athleteParameter = new AthleteParameter(athlete, athleteParameterType, LocalDate.now(), athleteParamValue);
        athleteParameterId = athleteParameterRepository.save(athleteParameter).getId();

        Coach coach = new Coach();
        coach.setName(coachName);
        coachId = coachRepository.save(coach).getId();

        Exercise exercise = new Exercise(exerciseName, "unit");
        exerciseId = exerciseRepository.save(exercise).getId();

        UserGroup userGroup = new UserGroup(userGroupName);
        userGroup.getMembers().add(user);
        userGroupId = userGroupRepository.save(userGroup).getId();

        TrainingGroup trainingGroup = new TrainingGroup(trainingGroupName);
        trainingGroup.setCoach(coach);
        trainingGroup.getMembers().add(athlete);
        trainingGroupId = trainingGroupRepository.save(trainingGroup).getId();

        Schedule schedule = new Schedule();
        schedule.setStartDate(LocalDate.now());
        scheduleId = scheduleRepository.save(schedule).getId();

        TrainingExercise trainingExercise = new TrainingExercise();
        trainingExercise.setExercise(exercise);

        TrainingSession trainingSession = new TrainingSession(schedule);
        trainingSession.addTrainingExercise(trainingExercise);
        trainingSession = trainingSessionRepository.save(trainingSession);
        trainingSessionId = trainingSession.getId();
        trainingExerciseId = trainingSession.getExercises().get(0).getId();

        ExerciseResult exerciseResult = new ExerciseResult(athlete, trainingExercise, 1, null, "value");
        exerciseResultId = exerciseResultRepository.save(exerciseResult).getId();

        UserPropertyType userPropertyType = new UserPropertyType(userPropertyName);
        userPropertyTypeId = userPropertyTypeRepository.save(userPropertyType).getId();

        UserProperty userProperty = new UserProperty(user, userPropertyType, userPropertyValue);
        userPropertyId = userPropertyRepository.save(userProperty).getId();

        setupIsDone = true;

    }

    @Test
    public void testUserRepo() {
        assertEquals(username, userRepository.getOne(userId).getUsername());
    }

    @Test
    public void testUserPropertyTypeRepo() {
        assertEquals(userPropertyName, userPropertyTypeRepository.getOne(userPropertyTypeId).getName());
    }

    @Test
    public void testUserPropertyRepo() {
        assertEquals(userPropertyValue, userPropertyRepository.getOne(userPropertyId).getValue());
    }

    @Test
    public void testAthleteRepo() {
        assertEquals(athleteName, athleteRepository.getOne(athleteId).getName());
    }

    @Test
    public void testAthleteParameterRepo() {
        assertEquals(athleteParamValue, athleteParameterRepository.getOne(athleteParameterId).getValue());
    }

    @Test
    public void testAthleteParameterTypeRepo() {
        assertEquals(athleteParamName, athleteParameterTypeRepository.getOne(athleteParameterTypeId).getName());
    }

    @Test
    public void testUserGroupRepo() {
        UserGroup userGroup = userGroupRepository.getOne(userGroupId);
        assertEquals(userGroupName, userGroup.getName());
        assertFalse(userGroup.getMembers().isEmpty());
    }

    @Test
    public void testTrainingGroupRepo() {
        TrainingGroup trainingGroup = trainingGroupRepository.getOne(trainingGroupId);
        assertEquals(trainingGroupName, trainingGroup.getName());
        assertFalse(trainingGroup.getMembers().isEmpty());
        assertEquals(coachId, trainingGroup.getCoach().getId());
    }

    @Test
    public void testTrainingSessionRepo() {
        TrainingSession trainingSession = trainingSessionRepository.getOne(trainingSessionId);
        assertFalse(trainingSession.getExercises().isEmpty());
        TrainingExercise trainingExercise = trainingExerciseRepository.getOne(trainingExerciseId);
        assertNotNull(trainingExercise.getTrainingSession());
    }
}