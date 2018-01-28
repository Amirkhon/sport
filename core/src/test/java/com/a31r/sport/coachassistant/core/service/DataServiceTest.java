package com.a31r.sport.coachassistant.core.service;

import com.a31r.sport.coachassistant.core.TestConfiguration;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Created by bahodurova on 1/26/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { TestConfiguration.class },
        loader = AnnotationConfigContextLoader.class)
public abstract class DataServiceTest<T> extends TestCase {

    @Test
    public void testFindById() {
        assertNotNull(getService().findById(getId()));
    }

    @Test
    public void testFindAll() {
        assertFalse(getService().findAll().isEmpty());
    }

    @Before
    public void before() {
        if (getDone()) {
            return;
        }
        setup();
        setDone(true);
    }

    protected abstract DataService<T> getService();
    protected abstract boolean getDone();
    protected abstract void setDone(boolean done);
    protected abstract long getId();
    protected abstract void setup();

    @Test
    public abstract void testInitialize();
    @Test
    public abstract void testSave();
    @Test
    public abstract void testDelete();

}
