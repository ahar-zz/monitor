package com.golo.monitor.scheduler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import com.golo.monitor.job.MerchantStatusJob;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonitorScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger("MonitorScheduler");
    private final Scheduler scheduler;

    @Autowired
    public MonitorScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void stop() {
        try {
            scheduler.deleteJob(MerchantStatusJob.KEY);
        } catch (SchedulerException e) {
            LOGGER.error("Failed to delete job {}", MerchantStatusJob.KEY, e);
        }
    }

    public void scheduleJobWithInterval(int interval) {
        try {
            scheduler.start();

            JobDetail job = newJob(MerchantStatusJob.class)
                    .withIdentity(MerchantStatusJob.KEY).build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .startNow()
                    .withSchedule(simpleSchedule().withIntervalInSeconds(interval).repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            LOGGER.error("Failed to start scheduler", e);
        }
    }
}
