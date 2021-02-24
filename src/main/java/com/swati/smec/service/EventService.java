package com.swati.smec.service;

import com.swati.smec.entity.Event;
import com.swati.smec.service.dto.EventDto;

import java.util.List;

public interface EventService {

    List<EventDto> listAllEvents();

    EventDto saveEvent(Event eventToBeSaved);

}
