package com.test.monitor.factory;

import com.test.monitor.entity.ServiceStatus;

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
