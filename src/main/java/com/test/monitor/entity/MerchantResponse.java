package com.test.monitor.entity;

public class MerchantResponse {
    private String status;

    public MerchantResponse() {
    }

    public MerchantResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
