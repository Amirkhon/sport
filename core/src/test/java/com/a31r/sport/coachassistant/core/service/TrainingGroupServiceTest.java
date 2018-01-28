package com.a31r.sport.coachassistant.core.service;

import com.a31r.sport.coachassistant.core.model.Athlete;
import com.a31r.sport.coachassistant.core.model.TrainingGroup;
import com.a31r.sport.coachassistant.core.model.TrainingSession;
import com.a31r.sport.coachassistant.core.model.repository.AthleteRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingGroupRepository;
import com.a31r.sport.coachassistant.core.model.repository.TrainingSessionRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by bahodurova on 1/27/2018.
 */
public class TrainingGroupServiceTest extends DataServiceTest<TrainingGroup> {

    @Resource
    private TrainingGroupService service;
    @Resource
    private TrainingGroupRepository repository;
    @Resource
    private AthleteRepository athleteRepository;
    @Resource
    private TrainingSessionRepository sessionRepository;

    private static boolean setupIsDone;
    private static long groupId;
    private static long athleteId;
    private static long sessionId;

    @Override
    protected DataService<TrainingGroup> getService() {
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
        return groupId;
    }

    @Override
    protected void setup() {
        Athlete athlete = new Athlete("AthleteN", "AthleteFN", "AthleteP");
        athlete = athleteRepository.save(athlete);
        athleteId = athlete.getId();
        TrainingSession session = new TrainingSession("TSName");
        TrainingGroup group = new TrainingGroup("TGName");
        group.addMember(athlete);
        group = repository.save(group);
        groupId = group.getId();
        session.addGroup(group);
        sessionId = sessionRepository.save(session).getId();
    }

    @Override
    public void testInitialize() {
        TrainingGroup group = repository.getOne(groupId);
        group = service.initialize(group);
        assertNotNull(group.getMembers());
        assertFalse(group.getMembers().isEmpty());
        assertEquals(1, group.getMembers().size());
        assertEquals(athleteId, group.getMembers().iterator().next().getId());
        assertNotNull(group.getSessions());
        assertFalse(group.getSessions().isEmpty());
        assertEquals(1, group.getSessions().size());
        assertEquals(sessionId, group.getSessions().iterator().next().getId());
    }

    @Override
    public void testSave() {
        Athlete athlete = new Athlete("NewAthleteN", "NewAthleteFN", "NewAthleteP");
        athlete = athleteRepository.save(athlete);
        long athleteId = athlete.getId();
        TrainingSession session = new TrainingSession("TSName");
        TrainingGroup group = new TrainingGroup("TGName");
        group.addMember(athlete);
        group = service.save(group);
        session.addGroup(group);
        long sessionId = sessionRepository.save(session).getId();
        assertFalse(group.getId() == 0);
        List<Athlete> athletes = athleteRepository.findAllByGroupsContains(group);
        assertFalse(athletes.isEmpty());
        assertEquals(athleteId, athletes.get(0).getId());
        List<TrainingSession> sessions = sessionRepository.findAllByGroupsContains(group);
        assertFalse(sessions.isEmpty());
        assertEquals(sessionId, sessions.get(0).getId());
    }

    @Override
    public void testDelete() {
        Athlete athlete = new Athlete("NewAthleteN", "NewAthleteFN", "NewAthleteP");
        athlete = athleteRepository.save(athlete);
        TrainingSession session = new TrainingSession("TSName");
        TrainingGroup group = new TrainingGroup("TGName");
        group.addMember(athlete);
        group = repository.save(group);
        session.addGroup(group);
        sessionRepository.save(session);
        service.delete(group);
        List<Athlete> athletes = athleteRepository.findAllByGroupsContains(group);
        assertTrue(athletes.isEmpty());
        List<TrainingSession> sessions = sessionRepository.findAllByGroupsContains(group);
        assertTrue(sessions.isEmpty());
    }
}
