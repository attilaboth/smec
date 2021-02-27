package com.swati.smec.util;

import com.swati.smec.entity.Account;
import com.swati.smec.entity.Event;
import com.swati.smec.helper.TestDataBuilder;
import com.swati.smec.service.dto.EventStat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class EventStatBuilderTest {

    @Test
    void buildResponseStatisticForAccount() {
        LocalDateTime now = LocalDateTime.now();

        Account account = TestDataBuilder.buildAnAccount("TestAccount");
        Set<Event> eventsSetForAccount = new HashSet<>(Arrays.asList(
                TestDataBuilder.buildAnEvent(account, "Synchonization completed", now),
                TestDataBuilder.buildAnEvent(account, "Synchonization started", TestDataBuilder.getTimestampUnitBefore(23, ChronoUnit.HOURS)),
                TestDataBuilder.buildAnEvent(account, "Synchonization started", TestDataBuilder.getTimestampUnitBefore(23, ChronoUnit.HOURS))
                )
        );

        List<EventStat> eventStats = EventStatBuilder.buildResponseStatisticForAccount(eventsSetForAccount);

        Assertions.assertNotNull(eventStats);
        Assertions.assertEquals(2, eventStats.size());
    }
}