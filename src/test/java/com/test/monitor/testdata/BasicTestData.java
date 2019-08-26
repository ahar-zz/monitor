package com.test.monitor.testdata;

import java.util.Arrays;
import java.util.List;

import com.test.monitor.entity.ServiceStatus;
import com.test.monitor.factory.ServiceStatusFactory;

public class BasicTestData {
    public class Domains {
        private String url;
        private boolean result;

        public Domains(String url, boolean result) {
            this.url = url;
            this.result = result;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    }

    private List<Domains> domains = Arrays.asList(
            new Domains("https://api.test.paysafe.com/", true),
            new Domains("https://domain.com/", true),
            new Domains("http://domain.com/", true),
            new Domains("http://dom.ain.com/", true),
            new Domains("https://do.ain.com/", true),
            new Domains("do.ain.com", false),
            new Domains("ftp://do.ain.com/", false),
            new Domains("http://do.ain.com/result/", true),
            new Domains("http://do.ain.com/", true),
            new Domains("http://do.ain.com/fdasfdas", true),
            new Domains("vcxzvczx", false)
    );

    private ServiceStatusFactory factory = new ServiceStatusFactory();

    private List<ServiceStatus> upTime = Arrays.asList(
            factory.up(),
            factory.up(),
            factory.up()
    );

    private List<ServiceStatus> downTime = Arrays.asList(
            factory.down(),
            factory.down(),
            factory.down(),
            factory.down(),
            factory.down()
    );

    public List<ServiceStatus> getUpTime() {
        return upTime;
    }

    public List<ServiceStatus> getDownTime() {
        return downTime;
    }

    public List<Domains> getDomains() {
        return domains;
    }
}
