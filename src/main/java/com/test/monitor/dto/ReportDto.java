package com.test.monitor.dto;

import com.test.monitor.entity.ServiceStatus;

import java.io.Serializable;
import java.util.List;

public class ReportDto implements Serializable {

    private List<ServiceStatus> upTime;
    private List<ServiceStatus> downTime;

    protected ReportDto() {
    }

    public ReportDto(List<ServiceStatus> upTime,
                     List<ServiceStatus> downTime) {
        this.upTime = upTime;
        this.downTime = downTime;
    }

    public List<ServiceStatus> getUpTime() {
        return upTime;
    }

    public void setUpTime(List<ServiceStatus> upTime) {
        this.upTime = upTime;
    }

    public List<ServiceStatus> getDownTime() {
        return downTime;
    }

    public void setDownTime(List<ServiceStatus> downTime) {
        this.downTime = downTime;
    }

    public static class Builder {
        private List<ServiceStatus> upTime;
        private List<ServiceStatus> downTime;

        public Builder() {
        }

        public Builder withUpTime(List<ServiceStatus> upTime) {
            this.upTime = upTime;
            return this;
        }

        public Builder withDownTime(List<ServiceStatus> downTime) {
            this.downTime = downTime;
            return this;
        }

        public ReportDto build() {
            return new ReportDto(upTime, downTime);
        }
    }
}
