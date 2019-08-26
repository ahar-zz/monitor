package com.test.monitor.service;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.test.monitor.dto.ReportDto;
import com.test.monitor.dto.MerchantServerDto;
import com.test.monitor.factory.ServiceStatusFactory;
import com.test.monitor.repository.ServiceStatusRepository;
import com.test.monitor.scheduler.MonitorScheduler;
import com.test.monitor.testdata.BasicTestData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MonitorServiceTest {
    @Mock
    ServiceStatusRepository repository;
    @Mock
    MonitorScheduler scheduler;
    ServiceStatusFactory factory = new ServiceStatusFactory();
    @InjectMocks
    MonitorService monitorService;

    BasicTestData testData = new BasicTestData();

    @Test
    public void stop_repository_should_not_be_cleared_after() {
        monitorService.stop();

        verify(repository, never()).clear();
        verify(scheduler, times(1)).stop();
    }

    @Test
    public void start_clear_repository_update_task() {
        MerchantServerDto dto = new MerchantServerDto("http://google.com", 10);
        monitorService.start(dto);

        verify(repository, times(1)).clear();
        verify(scheduler, times(1)).scheduleJobWithInterval(dto.getInterval());
    }

    @Test
    public void getReport() {
        when(repository.getServerUpTimes()).thenReturn(testData.getUpTime());
        when(repository.getServerDownTimes()).thenReturn(testData.getDownTime());

        ReportDto reportDto = monitorService.getReport();

        verify(repository, times(1)).getServerDownTimes();
        verify(repository, times(1)).getServerUpTimes();

        assertEquals(testData.getDownTime().size(), reportDto.getDownTime().size());
        assertEquals(testData.getUpTime().size(), reportDto.getUpTime().size());
    }
}