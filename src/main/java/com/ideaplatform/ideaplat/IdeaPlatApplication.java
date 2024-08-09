package com.ideaplatform.ideaplat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class IdeaPlatApplication {

    public static void main(String[] args) {
        InputStream file = IdeaPlatApplication.class.getResourceAsStream("/tickets.json");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        TicketCalculator ticketCalculator = new TicketCalculator();

        try {
            Tickets tickets = objectMapper.readValue(file, new TypeReference<Tickets>(){});

            ticketCalculator.calculateMinimumTravelTime(tickets.getTickets());

            ticketCalculator.calculateDifferenceBetweenMedianAndMeanPrices(tickets.getTickets());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
