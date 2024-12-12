package com.iit.RealtimeTicketing.CLI;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.File;
import java.io.IOException;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class WebConfiguration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;


    // Getters and Setters
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Validation Method
    public boolean validate() {
        if (totalTickets <= 0 || ticketReleaseRate <= 0 || customerRetrievalRate <= 0 || maxTicketCapacity <= 0) {
            System.out.println("All fields must be positive numbers");
            return false;
        }
        if (totalTickets > maxTicketCapacity) {
            System.out.println("Total number of tickets can't exceed the maximum ticket capacity");
            return false;
        }
        return true;
    }

    // Save to File Method Using Jackson
    public void saveToFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(filePath), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load from File Method Using Jackson
    public static WebConfiguration loadFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(filePath), WebConfiguration.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}