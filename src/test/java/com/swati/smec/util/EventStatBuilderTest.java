package com.swati.smec.util;

import com.swati.smec.entity.Account;
import com.swati.smec.entity.Event;
import com.swati.smec.service.dto.EventStat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class EventStatBuilderTest {

    @Test
    void buildResponseStatisticForAccount() {
        LocalDateTime now = LocalDateTime.now();

        Account account = buildAnAccount("TestAccount");
        Set<Event> eventsSetForAccount = Set.copyOf(Arrays.asList(
                buildAnEvent(account, "Synchonization completed", now),
                buildAnEvent(account, "Synchonization started", getTimestampUnitBefore(23, ChronoUnit.HOURS)),
                buildAnEvent(account, "Synchonization started", getTimestampUnitBefore(23, ChronoUnit.HOURS))
                )
        );

        List<EventStat> eventStats = EventStatBuilder.buildResponseStatisticForAccount(eventsSetForAccount);

        Assertions.assertNotNull(eventStats);
        Assertions.assertEquals(2, eventStats.size());
    }


    private LocalDateTime getTimestampUnitBefore(int ammount, ChronoUnit chronoUnit) {
        return LocalDateTime.now().minus(ammount, chronoUnit);
    }

    private List<Event> getNumberOfEvents(Account account, int num, String eventType) {
        List<Event> eventList = IntStream.rangeClosed(1, num)
                .mapToObj(event -> Event.builder()
                        .account(account)
                        .eventName(eventType)
                        .dateCreated(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        return eventList;
    }

    private Event buildAnEvent(Account account, String eventType, LocalDateTime timeStamp) {
        return Event.builder()
                .account(account)
                .eventName(eventType)
                .dateCreated(timeStamp)
                .build();
    }

    private Account buildAnAccount(String accountName) {
        return Account.builder().accountName(accountName).build();
    }
}