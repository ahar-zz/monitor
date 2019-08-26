package com.test.monitor.repository;

import static org.junit.Assert.*;

import com.test.monitor.entity.ServiceStatus;
import com.test.monitor.factory.ServiceStatusFactory;
import com.test.monitor.testdata.BasicTestData;

import org.junit.Test;

public class ServiceStatusRepositoryIntegrationTest {

    private ServiceStatusFactory factory = new ServiceStatusFactory();
    private ServiceStatusRepository repository = new ServiceStatusRepository();

    BasicTestData testData = new BasicTestData();

    @Test
    public void emptyOnFirstRun() {
        assertEquals(0, repository.getServerDownTimes().size());
        assertEquals(0, repository.getServerUpTimes().size());
    }

    @Test
    public void selectData() {
        repository.save(testData.getDownTime());
        repository.save(testData.getUpTime());

        assertEquals(5, repository.getServerDownTimes().size());
        assertEquals(3, repository.getServerUpTimes().size());
    }

    @Test
    public void serviceStatusRemainsTheSameAfterSave() {
        ServiceStatus serviceStatus = factory.down();

        ServiceStatus saved = repository.save(serviceStatus);

        assertEquals(saved, serviceStatus);
    }

    @Test
    public void repositoryIsEmptyAfterClean() {
        repository.save(testData.getDownTime());
        repository.save(testData.getUpTime());

        repository.clear();

        assertEquals(0, repository.getServerUpTimes().size());
        assertEquals(0, repository.getServerDownTimes().size());
    }
}