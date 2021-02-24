package com.swati.smec.service;

import com.swati.smec.entity.Event;
import com.swati.smec.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> listAllEvents() {
        return eventRepository.findAll();
    }

    public Event addEvent(Event newEvent) {
        Event savedEvent = eventRepository.save(newEvent);
        return savedEvent;
    }

}
