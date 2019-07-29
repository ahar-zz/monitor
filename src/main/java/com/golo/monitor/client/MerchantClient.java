package com.golo.monitor.client;

import com.golo.monitor.entity.MerchantResponse;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import rx.Observable;

@FeignClient(name = "merchant-client", fallback = com.golo.monitor.client.fallback.MerchantClientFallback.class)
public interface MerchantClient {

    @RequestLine("GET /accountmanagement/monitor")
    Observable<MerchantResponse> monitor();

}
