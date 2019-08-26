package com.test.monitor.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.test.monitor.constant.RegexPatterns;

public class MerchantServerDto {

    @Pattern(regexp = RegexPatterns.URL,
            message = "Invalid url, please use domain names with protocols, ex.: " +
                    "http://google.com/, https://medium.com/")
    private String url;
    @NotNull
    @Min(value = 1, message = "Interval should be more than 1 sec")
    private int interval;

    public MerchantServerDto() {
    }

    public MerchantServerDto(
            String url, int interval) {
        this.url = url;
        this.interval = interval;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
