package com.golo.monitor.service;

import com.golo.monitor.client.MerchantClient;
import com.golo.monitor.dto.MerchantServerDto;
import com.golo.monitor.dto.ReportDto;
import com.golo.monitor.factory.ServiceStatusFactory;
import com.golo.monitor.fallback.MerchantClientFallback;
import com.golo.monitor.repository.ServiceStatusRepository;
import com.golo.monitor.scheduler.MonitorScheduler;

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
