package com.test.monitor.repository;

import com.test.monitor.entity.ServiceStatus;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ServiceStatusRepository {

    private Set<ServiceStatus> serviceStatusSet = new LinkedHashSet<>();

    public void save(List<ServiceStatus> serviceStatusList) {
        serviceStatusSet.addAll(serviceStatusList);
    }

    public ServiceStatus save(ServiceStatus serviceStatus) {
        serviceStatusSet.add(serviceStatus);
        return serviceStatus;
    }

    public List<ServiceStatus> getServerUpTimes(){
        return serviceStatusSet.stream()
                .filter(serviceStatus -> serviceStatus.getResponseStatus().equals(ServiceStatus.ResponseStatus.SUCCESS))
                .collect(Collectors.toList());
    }

    public List<ServiceStatus> getServerDownTimes(){
        return serviceStatusSet.stream()
                .filter(serviceStatus -> serviceStatus.getResponseStatus().equals(ServiceStatus.ResponseStatus.FAILURE))
                .collect(Collectors.toList());
    }

    public void clear() {
        serviceStatusSet.clear();
    }
}
