package com.golo.monitor.client;

import com.golo.monitor.entity.MerchantResponse;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "merchant-client")
public interface MerchantClient {

    @RequestLine("GET /accountmanagement/monitor")
    MerchantResponse monitor();

}
