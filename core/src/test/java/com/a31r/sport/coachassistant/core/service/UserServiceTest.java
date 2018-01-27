package com.a31r.sport.coachassistant.core.service;

import com.a31r.sport.coachassistant.core.model.User;
import com.a31r.sport.coachassistant.core.model.UserGroup;
import com.a31r.sport.coachassistant.core.model.UserProperty;
import com.a31r.sport.coachassistant.core.model.UserPropertyType;
import com.a31r.sport.coachassistant.core.model.repository.UserGroupRepository;
import com.a31r.sport.coachassistant.core.model.repository.UserPropertyRepository;
import com.a31r.sport.coachassistant.core.model.repository.UserPropertyTypeRepository;
import com.a31r.sport.coachassistant.core.model.repository.UserRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by bahodurova on 1/27/2018.
 */
public class UserServiceTest extends DataServiceTest<User> {

    @Resource
    private UserService service;

    @Resource
    private UserPropertyTypeRepository propertyTypeRepository;

    @Resource
    private UserPropertyRepository propertyRepository;

    @Resource
    private UserGroupRepository groupRepository;

    @Resource
    private UserRepository repository;

    private static boolean setupIsDone = false;
    private static long userId;
    private static long groupId;

    @Override
    protected boolean getDone() {
        return setupIsDone;
    }

    @Override
    protected void setDone(boolean done) {
        setupIsDone = done;
    }

    @Override
    public void setup() {
        User user = new User("Name", "Family", "Patronymic");
        user = repository.save(user);
        UserPropertyType propertyType = new UserPropertyType("Phone");
        propertyType = propertyTypeRepository.save(propertyType);
        UserProperty property = new UserProperty(propertyType, "1234567890");
        user.addProperty(property);
        user = repository.save(user);
        userId = user.getId();
        UserGroup group = new UserGroup("Group");
        group.addMember(user);
        groupId = groupRepository.save(group).getId();
    }

    @Override
    protected DataService<User> getService() {
        return service;
    }

    @Override
    public long getId() {
        return userId;
    }

    @Override
    public void testInitialize() {
        User user = repository.getOne(userId);
        user = service.initialize(user);
        assertNotNull(user.getProperties());
        assertFalse(user.getProperties().isEmpty());
        assertEquals(1, user.getProperties().size());
        assertNotNull(user.getGroups());
        assertFalse(user.getGroups().isEmpty());
        assertEquals(1, user.getGroups().size());
        assertEquals(groupId, user.getGroups().iterator().next().getId());
    }

    @Override
    public void testSave() {
        User user = new User("NewUser", "Family", "Patronymic");
        UserPropertyType propertyType = new UserPropertyType("Email");
        propertyType = propertyTypeRepository.save(propertyType);
        UserProperty property = new UserProperty(propertyType, "a@mail.com");
        user.addProperty(property);
        user = service.save(user);
        assertFalse(user.getId() == 0);
        List<UserProperty> properties = propertyRepository.findAllByUser(user);
        for (UserProperty p : properties) {
            if (p.getPropertyType().equals(propertyType)) {
                assertEquals("a@mail.com", p.getValue());
            }
        }
    }

    @Override
    public void testDelete() {
        User user = new User("NewUser", "Family", "Patronymic");
        user = repository.save(user);
        long userId = user.getId();
        service.delete(user);
        assertFalse(repository.existsById(userId));
    }

}