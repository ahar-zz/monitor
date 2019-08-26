package com.test.monitor.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ServiceStatus {

    public enum ResponseStatus {
        SUCCESS, FAILURE
    }

    @JsonIgnore
    private ResponseStatus responseStatus;
    private Date date = new Date();

    protected ServiceStatus() {
    }

    public ServiceStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Date getDate() {
        return date;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
