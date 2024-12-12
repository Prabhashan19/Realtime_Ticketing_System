package com.iit.RealtimeTicketing.controller;


import com.iit.RealtimeTicketing.CLI.Main;
import com.iit.RealtimeTicketing.service.SimService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/system")
@CrossOrigin
public class SystemController {

    private SimService simService;
    private Main main;

    public SystemController(SimService simService, Main main) {
        this.simService = simService;
        this.main = main;
    }

    @GetMapping("/start")
    public ResponseEntity<String> startSystem() {
        // Start system logic
        simService.startSimulation();
        return ResponseEntity.ok("System started successfully");
    }


    @GetMapping("/stop")
    public ResponseEntity<String> stopSystem() {
        simService.stopSimulation();
        // Stop system logic
        return ResponseEntity.ok("System stopped successfully");
    }
}
