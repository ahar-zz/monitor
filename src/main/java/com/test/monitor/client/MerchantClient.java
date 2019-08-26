package com.test.monitor.client;

import com.test.monitor.entity.MerchantResponse;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "merchant-client")
public interface MerchantClient {

    @RequestLine("GET /accountmanagement/monitor")
    MerchantResponse monitor();

}
