package com.a31r.sport.coachassistant.core.service;

import com.a31r.sport.coachassistant.core.model.User;
import com.a31r.sport.coachassistant.core.model.UserGroup;
import com.a31r.sport.coachassistant.core.model.repository.UserGroupRepository;
import com.a31r.sport.coachassistant.core.model.repository.UserRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by bahodurova on 1/27/2018.
 */
public class UserGroupServiceTest extends DataServiceTest<UserGroup> {

    @Resource
    private UserGroupService service;
    @Resource
    private UserGroupRepository repository;
    @Resource
    private UserRepository userRepository;

    private static boolean setupIsDone;
    private static long groupId;
    private static long userId;

    @Override
    protected DataService<UserGroup> getService() {
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
        User user = new User("UserN", "UserFN", "UserP");
        user = userRepository.save(user);
        userId = user.getId();
        UserGroup group = new UserGroup("UGName");
        group.addMember(user);
        groupId = repository.save(group).getId();
    }

    @Override
    public void testInitialize() {
        UserGroup group = repository.getOne(groupId);
        group = service.initialize(group);
        assertNotNull(group.getMembers());
        assertFalse(group.getMembers().isEmpty());
        assertEquals(1, group.getMembers().size());
        assertEquals(userId, group.getMembers().iterator().next().getId());
    }

    @Override
    public void testSave() {
        User user = new User("NewUserN", "NewUserFN", "NewUserP");
        user = userRepository.save(user);
        long userId = user.getId();
        UserGroup group = new UserGroup("NewUGName");
        group.addMember(user);
        group = service.save(group);
        assertTrue(group.getId() != 0);
        List<User> users = userRepository.findAllByGroupsContains(group);
        assertFalse(users.isEmpty());
        assertEquals(userId, users.get(0).getId());
    }

    @Override
    public void testDelete() {
        User user = new User("NewUserN", "NewUserFN", "NewUserP");
        user = userRepository.save(user);
        UserGroup group = new UserGroup("NewUGName");
        group.addMember(user);
        group = repository.save(group);
        service.delete(group);
        List<User> users = userRepository.findAllByGroupsContains(group);
        assertTrue(users.isEmpty());
    }
}
