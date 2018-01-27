package com.a31r.sport.coachassistant.core.service;

import com.a31r.sport.coachassistant.core.model.Coach;
import com.a31r.sport.coachassistant.core.model.TrainingGroup;
import com.a31r.sport.coachassistant.core.model.TrainingSession;
import com.a31r.sport.coachassistant.core.model.repository.CoachRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingGroupRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingSessionRepository;

import javax.annotation.Resource;

/**
 * Created by bahodurova on 1/27/2018.
 */
public class CoachServiceTest extends DataServiceTest<Coach> {

    @Resource
    private CoachRepository repository;

    @Resource
    private TrainingGroupRepository groupRepository;

    @Resource
    private TrainingSessionRepository sessionRepository;

    @Resource
    private CoachService service;

    private static boolean setupIsDone = false;
    private static long coachId;
    private static long groupId;
    private static long sessionId;

    @Override
    protected boolean getDone() {
        return setupIsDone;
    }

    @Override
    protected void setDone(boolean done) {
        setupIsDone = done;
    }

    @Override
    protected DataService<Coach> getService() {
        return service;
    }

    @Override
    public long getId() {
        return coachId;
    }

    @Override
    public void setup() {
        Coach coach = new Coach("CoachN", "CoachFN", "CoachP");
        coach = repository.save(coach);
        coachId = coach.getId();
        TrainingGroup group = new TrainingGroup("TGroup");
        group.setCoach(coach);
        groupId = groupRepository.save(group).getId();
        TrainingSession session = new TrainingSession("TSName");
        session.setCoach(coach);
        sessionId = sessionRepository.save(session).getId();
    }

    @Override
    public void testInitialize() {
        Coach coach = repository.getOne(coachId);
        coach = service.initialize(coach);
        assertNotNull(coach.getTrainingGroups());
        assertNotNull(coach.getTrainingSessions());
        assertFalse(coach.getTrainingGroups().isEmpty());
        assertFalse(coach.getTrainingSessions().isEmpty());
        assertEquals(1, coach.getTrainingGroups().size());
        assertEquals(1, coach.getTrainingSessions().size());
        assertEquals(groupId, coach.getTrainingGroups().iterator().next().getId());
        assertEquals(sessionId, coach.getTrainingSessions().iterator().next().getId());
    }

    @Override
    public void testSave() {
        Coach coach = new Coach("CoachN", "CoachFN", "CoachP");
        coach = service.save(coach);
        assertTrue(coach.getId() != 0);
        assertNotNull(repository.findById(coach.getId()).get());
    }

    @Override
    public void testDelete() {
        Coach coach = new Coach("NewCoachN", "NewCoachFN", "NewCoachP");
        coach = repository.save(coach);
        TrainingGroup group = new TrainingGroup("NewTGName");
        group.setCoach(coach);
        groupId = groupRepository.save(group).getId();
        TrainingSession session = new TrainingSession("NewTSName");
        session.setCoach(coach);
        sessionId = sessionRepository.save(session).getId();
        service.delete(coach);
        assertTrue(groupRepository.findAllByCoach(coach).isEmpty());
        assertTrue(sessionRepository.findAllByCoach(coach).isEmpty());
        assertFalse(repository.existsById(coach.getId()));
    }
}
