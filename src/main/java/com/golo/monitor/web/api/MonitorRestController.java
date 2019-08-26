package com.golo.monitor.web.api;

import com.golo.monitor.dto.MerchantServerDto;
import com.golo.monitor.dto.ReportDto;
import com.golo.monitor.service.MonitorService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/monitor")
public class MonitorRestController {

    private final MonitorService service;

    @Autowired
    public MonitorRestController(MonitorService service) {
        this.service = service;
    }

    @ApiOperation(value = "API call to start monitoring server")
    @PostMapping(value = "/start")
    public ResponseEntity startMonitoring(@RequestBody @Valid MerchantServerDto propertiesDto) {
        service.start(propertiesDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "API call to stop monitoring server")
    @PostMapping(value = "/stop")
    public ResponseEntity stopMonitoring() {
        service.stop();
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "API call to get report of last monitoring session")
    @GetMapping(value = "/report")
    public ResponseEntity<ReportDto> getReport() {
        ReportDto serviceStatus = service.getReport();
        return ResponseEntity.ok(serviceStatus);
    }

}
