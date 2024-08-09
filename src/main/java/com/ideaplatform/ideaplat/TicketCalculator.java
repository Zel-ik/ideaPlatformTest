package com.ideaplatform.ideaplat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketCalculator {

    public void calculateMinimumTravelTime(List<Ticket> ticketList){

        Map<String, LocalTime> ticketsMap = new HashMap<>();

        ticketList.stream()
                .filter(x -> x.getOriginName().equals("Владивосток") && x.getDestinationName().equals("Тель-Авив"))
                .forEach(x -> {
                    LocalDateTime departureLocalDateTime = LocalDateTime.of(x.getDepartureDate(), x.getDepartureTime());
                    LocalDateTime arrivalLocalDateTime = LocalDateTime.of(x.getArrivalDate(), x.getArrivalTime());
                    LocalTime travelTimeDifference = LocalTime.ofSecondOfDay(departureLocalDateTime.until(arrivalLocalDateTime, ChronoUnit.SECONDS));

                    if(!ticketsMap.containsKey(x.getCarrier())){
                        ticketsMap.put(x.getCarrier(),
                                travelTimeDifference);
                    }else if(ticketsMap.get(x.getCarrier()).isAfter(travelTimeDifference)){
                        ticketsMap.put(x.getCarrier(), travelTimeDifference);
                    }
                });

        for(String carrier : ticketsMap.keySet()){
            System.out.printf("минимальное время полета у авиаперевозчика %s равняется %tH:%tM часов%n",
                    carrier,
                    ticketsMap.get(carrier),
                    ticketsMap.get(carrier)
            );
        }
    }


    public void calculateDifferenceBetweenMedianAndMeanPrices(List<Ticket> ticketList){
        int medianPrice = 0;
        int meanPrice = 0;
        List<Integer> pricesList= ticketList.stream()
                .filter(x -> x.getOriginName().equals("Владивосток") && x.getDestinationName().equals("Тель-Авив"))
                .sorted(Comparator.comparingInt(Ticket::getPrice))
                .map(Ticket::getPrice)
                .toList();

        medianPrice = (pricesList.size() % 2 != 0) ? pricesList.get((pricesList.size()-1)/2) :
                (pricesList.get((pricesList.size())/2) + pricesList.get((pricesList.size()-2)/2))/2;

        meanPrice = pricesList.stream().reduce(0, Integer::sum)/pricesList.size();

        System.out.printf("медианая цена: %d;\nсредняя арифметическая цена: %d;\n" +
                "разница между медианой и средней арифметической: %d",
                medianPrice, meanPrice, Math.abs(medianPrice - meanPrice));
    }
}
