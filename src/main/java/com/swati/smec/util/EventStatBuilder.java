package com.swati.smec.util;

import com.swati.smec.entity.Event;
import com.swati.smec.service.dto.EventStat;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EventStatBuilder {

    private EventStatBuilder() {
        throw new IllegalStateException("EventStatBuilder is a Utility class");
    }

    public static List<EventStat> buildResponseStatisticForAccount(Set<Event> eventsSetForAccount) {
        List<EventStat> eventStatsList = Collections.synchronizedList(new ArrayList<>());

        Map<LocalDate, List<Event>> eventsGroupByDate = eventsSetForAccount.stream()
                .collect(Collectors.groupingBy(e -> e.getDateCreated().toLocalDate()));

        eventsGroupByDate.forEach((localDate, events) -> {
            Map<String, Long> eventStatMap = events.stream()
                    .collect(Collectors.groupingBy(Event::getEventName, Collectors.counting()));

            eventStatMap.forEach((eventType, value) ->
                    eventStatsList.add(EventStat.builder()
                            .day(localDate)
                            .eventType(eventType)
                            .count(value)
                            .build())
            );
        });
        return eventStatsList;
    }
}
