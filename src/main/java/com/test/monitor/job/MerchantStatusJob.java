package com.test.monitor.job;

import com.test.monitor.service.MonitorService;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;

public class MerchantStatusJob implements Job {
    public static final JobKey KEY = JobKey.jobKey("merchantStatusJob");

    MonitorService monitoringService;

    @Autowired
    public MerchantStatusJob(MonitorService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @Override
    public void execute(JobExecutionContext context) {
        monitoringService.checkMerchant();
    }
}
