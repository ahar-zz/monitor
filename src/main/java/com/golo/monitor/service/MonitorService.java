package com.golo.monitor.service;

import com.golo.monitor.client.MerchantClient;
import com.golo.monitor.dto.MerchantServerDto;
import com.golo.monitor.dto.ReportDto;
import com.golo.monitor.factory.ServiceStatusFactory;
import com.golo.monitor.repository.ServiceStatusRepository;
import com.golo.monitor.scheduler.MonitorScheduler;
import com.google.common.base.Strings;

import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

@Service
public class MonitorService {
    private static final Logger LOGGER = LoggerFactory.getLogger("MonitoringService");

    private MerchantClient apiClient;
    private final ServiceStatusRepository serviceStatusRepository;
    private final MonitorScheduler schedulerHelper;
    private final ServiceStatusFactory statusFactory;

    @Autowired
    public MonitorService(ServiceStatusRepository serviceStatusRepository,
                          MonitorScheduler schedulerHelper,
                          ServiceStatusFactory statusFactory) {
        this.serviceStatusRepository = serviceStatusRepository;
        this.schedulerHelper = schedulerHelper;
        this.statusFactory = statusFactory;
    }

    public void start(MerchantServerDto serverPropertiesDto) {
        serviceStatusRepository.clear();
        newTask(serverPropertiesDto.getUrl(),
                serverPropertiesDto.getInterval());
    }

    private void newTask(String url, int interval) {
        schedulerHelper.scheduleJobWithInterval(interval);
        if (!Strings.isNullOrEmpty(url)) {
            apiClient = HystrixFeign.builder()
                    .decoder(new JacksonDecoder())
                    .target(MerchantClient.class, url);
        } else {
            stop();
        }
    }

    public void stop() {
        schedulerHelper.stop();
        apiClient = null;
    }

    public void checkMerchant(){
        if (apiClient != null) {
            apiClient.monitor()
                    .map(response -> response.getStatus().equals("READY") ?
                            statusFactory.up() : statusFactory.down())
                    .onErrorResumeNext(throwable -> {
                        LOGGER.error("Request failed", throwable);
                        return Observable.just(statusFactory.down());
                    })
                    .subscribe(serviceStatusRepository::save);
        }
    }

    public ReportDto getReport(){
        return new ReportDto.Builder()
                .withUpTime(serviceStatusRepository.getServerUpTimes())
                .withDownTime(serviceStatusRepository.getServerDownTimes())
                .build();
    }
}
