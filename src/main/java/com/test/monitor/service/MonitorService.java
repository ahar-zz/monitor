package com.test.monitor.service;

import com.test.monitor.client.MerchantClient;
import com.test.monitor.dto.MerchantServerDto;
import com.test.monitor.dto.ReportDto;
import com.test.monitor.factory.ServiceStatusFactory;
import com.test.monitor.fallback.MerchantClientFallback;
import com.test.monitor.repository.ServiceStatusRepository;
import com.test.monitor.scheduler.MonitorScheduler;

import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

@Service
public class MonitorService {
    private MerchantClient apiClient;
    private final ServiceStatusRepository serviceStatusRepository;
    private final MonitorScheduler schedulerHelper;
    private final ServiceStatusFactory statusFactory;
    private final MerchantClientFallback merchantClientFallback;

    @Autowired
    public MonitorService(ServiceStatusRepository serviceStatusRepository,
                          MonitorScheduler schedulerHelper,
                          ServiceStatusFactory statusFactory,
                          MerchantClientFallback merchantClientFallback) {
        this.serviceStatusRepository = serviceStatusRepository;
        this.schedulerHelper = schedulerHelper;
        this.statusFactory = statusFactory;
        this.merchantClientFallback = merchantClientFallback;
    }

    public void start(MerchantServerDto serverPropertiesDto) {
        serviceStatusRepository.clear();
        newTask(serverPropertiesDto.getUrl(),
                serverPropertiesDto.getInterval());
    }

    private void newTask(String url, int interval) {
        schedulerHelper.scheduleJobWithInterval(interval);
        apiClient = HystrixFeign.builder()
                .client(new OkHttpClient())
                .decoder(new JacksonDecoder())
                .target(MerchantClient.class, url, merchantClientFallback);
    }

    public void stop() {
        schedulerHelper.stop();
        apiClient = null;
    }

    public void checkMerchant(){
        if (apiClient != null) {
            Observable.just(apiClient.monitor())
                    .map(response -> response.getStatus().equals("READY") ?
                            statusFactory.up() : statusFactory.down())
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
