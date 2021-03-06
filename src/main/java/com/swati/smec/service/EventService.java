package com.swati.smec.service;

import com.swati.smec.entity.Event;
import com.swati.smec.service.dto.EventDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventService {

    List<EventDto> listAllEvents();

    Optional<EventDto> saveEvent(Event eventToBeSaved);

    Optional<List<Event>>findByDateCreatedIsAfter(LocalDateTime afterThislocalDateTime);


}
