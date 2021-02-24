package com.swati.smec.controller;

import com.swati.smec.entity.Event;
import com.swati.smec.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/events", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Event>> getAllAccounts() {

        List<Event> events = eventService.listAllEvents();

        return ResponseEntity.ok(events);
    }
}
