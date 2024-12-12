package com.iit.RealtimeTicketing.controller;


import com.iit.RealtimeTicketing.CLI.WebConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/config")
@CrossOrigin
public class ConfigController {

    @PostMapping("update")
    public ResponseEntity<String> updateConfig(@Validated @RequestBody WebConfiguration config) {
        config.setTotalTickets(config.getTotalTickets());
        config.setTicketReleaseRate(config.getTicketReleaseRate());
        config.setCustomerRetrievalRate(config.getCustomerRetrievalRate());
        config.setMaxTicketCapacity(config.getMaxTicketCapacity());

        config.saveToFile("config.json");
        return ResponseEntity.ok("Configuration Successfully Updated!");
    }
}
