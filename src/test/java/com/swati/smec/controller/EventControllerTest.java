package com.swati.smec.controller;

import com.swati.smec.entity.Account;
import com.swati.smec.entity.Event;
import com.swati.smec.helper.TestDataBuilder;
import com.swati.smec.service.AccountService;
import com.swati.smec.service.EventService;
import com.swati.smec.service.dto.EventDto;
import com.swati.smec.service.dto.EventToAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class EventControllerTest {

    @Mock
    EventService eventService;
    @Mock
    AccountService accountService;

    @InjectMocks
    EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEvents() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Account testAccount = TestDataBuilder.buildAnAccount("TestAccount");
        Event testEvent = TestDataBuilder.buildAnEvent(testAccount, "Synchonization started", TestDataBuilder.getTimestampUnitBefore(23, ChronoUnit.HOURS));
        testAccount.getEvents().add(testEvent);

        List<EventDto> testEventDtoList = new ArrayList<>();
        testEventDtoList.add(new ModelMapper().map(testEvent, EventDto.class));

        //when
        when(eventService.listAllEvents()).thenReturn(testEventDtoList);
        ResponseEntity<List<EventDto>> result = eventController.getAllEvents();

        //then
        List<EventDto> body = result.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(1, body.size());
        Assertions.assertEquals("Synchonization started", body.get(0).getEventName());
    }

    @Test
    void testAddEventToAccount() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Account testAccount = TestDataBuilder.buildAnAccount("TestAccount");
        Event testEvent = TestDataBuilder.buildAnEvent(testAccount, "Synchonization started", TestDataBuilder.getTimestampUnitBefore(23, ChronoUnit.HOURS));
        testAccount.getEvents().add(testEvent);

        EventDto eventDto = new ModelMapper().map(testEvent, EventDto.class);
        EventToAccount eventToAccount = new EventToAccount();
        eventToAccount.setAccountName("TestAccount");
        eventToAccount.setEventName("Synchonization started");

        //when
        when(eventService.saveEvent(any())).thenReturn(Optional.of(eventDto));
        when(accountService.findByAccountName(anyString())).thenReturn(Optional.of(testAccount));
        ResponseEntity<EventDto> result = eventController.addEventToAccount(eventToAccount);

        //then
        EventDto resultBody = result.getBody();
        Assertions.assertNotNull(resultBody);
        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertEquals("Synchonization started", resultBody.getEventName());
    }
}
