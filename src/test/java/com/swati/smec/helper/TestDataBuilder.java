package com.swati.smec.helper;

import com.swati.smec.entity.Account;
import com.swati.smec.entity.Event;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestDataBuilder {

    public static Event buildAnEvent(Account account, String eventType, LocalDateTime timeStamp) {
        return Event.builder()
                .account(account)
                .eventName(eventType)
                .dateCreated(timeStamp)
                .build();
    }

    public static Account buildAnAccount(String accountName) {
        return Account.builder().accountName(accountName).build();
    }

    public static LocalDateTime getTimestampUnitBefore(int ammount, ChronoUnit chronoUnit) {
        return LocalDateTime.now().minus(ammount, chronoUnit);
    }

    public static List<Event> getNumberOfEvents(Account account, int num, String eventType) {
        List<Event> eventList = IntStream.rangeClosed(1, num)
                .mapToObj(event -> Event.builder()
                        .account(account)
                        .eventName(eventType)
                        .dateCreated(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        return eventList;
    }

}
