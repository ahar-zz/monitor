package com.test.monitor.web.api;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


import com.test.monitor.dto.ReportDto;
import com.test.monitor.dto.MerchantServerDto;
import com.test.monitor.service.MonitorService;
import com.test.monitor.testdata.BasicTestData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class MonitoringRestControllerTest {
    private static final String START_URL = "/api/monitor/start";
    private static final String STOP_URL = "/api/monitor/stop";
    private static final String REPORT_URL = "/api/monitor/report";

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    MonitorService monitorService;
    BasicTestData testData = new BasicTestData();
    ReportDto reportDto;

    @Before
    public void setUp() throws Exception {
        reportDto = new ReportDto(testData.getUpTime(), testData.getDownTime());
    }

    @Test
    public void start_with_get() {
        ResponseEntity<Void> response = restTemplate.exchange(START_URL, HttpMethod.GET,null,
                Void.class);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
    }

    @Test
    public void start_with_post() {
        MerchantServerDto dto = new MerchantServerDto("http://google.com", 10);
        ResponseEntity<Void> response = restTemplate.exchange(START_URL, HttpMethod.POST,new HttpEntity<>(dto),
                Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(monitorService).start(any(MerchantServerDto.class));
    }

    @Test
    public void stop_with_get() {
        ResponseEntity<Void> response = restTemplate.exchange(STOP_URL, HttpMethod.GET,null,
                Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(monitorService).stop();
    }

    @Test
    public void report() {
        when(monitorService.getReport()).thenReturn(reportDto);
        ResponseEntity<ReportDto> response  = restTemplate.getForEntity(REPORT_URL, ReportDto.class);

        verify(monitorService).getReport();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reportDto.getDownTime().size(), response.getBody().getDownTime().size());
        assertEquals(reportDto.getUpTime().size(), response.getBody().getUpTime().size());
    }
}