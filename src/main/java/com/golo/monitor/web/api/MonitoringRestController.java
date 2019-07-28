package com.golo.monitor.web.api;

import com.golo.monitor.dto.MerchantServerDto;
import com.golo.monitor.dto.ReportDto;
import com.golo.monitor.service.MonitorService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/monitor")
public class MonitoringRestController {

    private final MonitorService service;

    @Autowired
    public MonitoringRestController(MonitorService service) {
        this.service = service;
    }

    @ApiOperation(value = "API call to start monitoring server")
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ResponseEntity startMonitoring(@RequestBody @Valid MerchantServerDto propertiesDto) {
        service.start(propertiesDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "API call to stop monitoring server")
    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public ResponseEntity stopMonitoring() {
        service.stop();
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "API call to get report of last monitoring session")
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ResponseEntity<ReportDto> getReport() {
        ReportDto serviceStatus = service.getReport();
        return ResponseEntity.ok(serviceStatus);
    }

}