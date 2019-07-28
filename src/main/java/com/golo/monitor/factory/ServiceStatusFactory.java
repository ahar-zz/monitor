package com.golo.monitor.factory;

import com.golo.monitor.entity.ServiceStatus;

import org.springframework.stereotype.Component;

@Component
public class ServiceStatusFactory {
    public ServiceStatus up() {
        return new ServiceStatus(ServiceStatus.ResponseStatus.SUCCESS);
    }

    public ServiceStatus down() {
        return new ServiceStatus(ServiceStatus.ResponseStatus.FAILURE);
    }
}
