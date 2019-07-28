package com.golo.monitor.client.fallback;

import com.golo.monitor.client.MerchantClient;
import com.golo.monitor.entity.MerchantResponse;

import org.springframework.stereotype.Component;
import rx.Observable;

@Component
public class MerchantClientFallback implements MerchantClient {

        @Override
        public Observable<MerchantResponse> monitor() {
            return Observable.just(new MerchantResponse("DOWN"));
        }
}