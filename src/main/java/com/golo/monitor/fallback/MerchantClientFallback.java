package com.golo.monitor.fallback;

import com.golo.monitor.client.MerchantClient;
import com.golo.monitor.entity.MerchantResponse;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MerchantClientFallback implements FallbackFactory<MerchantClient> {
    private static final Logger LOGGER = LoggerFactory.getLogger("MonitoringService");

    @Override
    public MerchantClient create(Throwable cause) {
        return () -> {
            LOGGER.error("Service is down, reason {}", (cause.getMessage() != null ? cause.getMessage() : "not known"));
            MerchantResponse merchantResponse = new MerchantResponse();
            merchantResponse.setStatus("DOWN");
            return merchantResponse;
        };
    }
}
