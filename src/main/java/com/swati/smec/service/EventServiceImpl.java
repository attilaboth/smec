package com.swati.smec.service;

import com.swati.smec.entity.Event;
import com.swati.smec.repository.EventRepository;
import com.swati.smec.service.dto.EventDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final ModelMapper modelMapper = new ModelMapper();

    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventDto> listAllEvents() {
        List<Event> allEventsFound = eventRepository.findAll();
        List<EventDto> allEventDtos = new ArrayList<>();
        //FIXME: make this method generic
        for (Event event : allEventsFound) {
            EventDto eventDto = modelMapper.map(event, EventDto.class);
            allEventDtos.add(eventDto);
        }
        return allEventDtos;
    }

    @Override
    public Optional<EventDto> saveEvent(Event eventToBeSaved) {
        EventDto eventDtoToReturn = null;
        Event savedEvent = eventRepository.save(eventToBeSaved);

        if(savedEvent != null){
            eventDtoToReturn = modelMapper.map(savedEvent, EventDto.class);

        }
        return Optional.ofNullable(eventDtoToReturn);
    }

}
