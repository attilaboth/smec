package com.swati.smec.controller;

import com.swati.smec.entity.Account;
import com.swati.smec.entity.Event;
import com.swati.smec.service.AccountService;
import com.swati.smec.service.EventService;
import com.swati.smec.service.dto.AccountDto;
import com.swati.smec.service.dto.EventDto;
import com.swati.smec.service.dto.EventToAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class EventController {

    private EventService eventService;
    private AccountService accountService;

    public EventController(EventService eventService, AccountService accountService) {
        this.eventService = eventService;
        this.accountService = accountService;
    }

    @GetMapping(value = "/events", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<EventDto>> getAllAccounts() {

        List<EventDto> events = eventService.listAllEvents();

        return ResponseEntity.ok(events);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/addEventToAccount",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EventDto> addEventToAccount(@RequestBody EventToAccount eventToAccount) {
        //FIXME: check if it is a whitespace , or only numbers.. etc
        log.info(" addEventToAccount() was called for Account:{} with eventName: {}", eventToAccount.getAccountName(), eventToAccount.getEventName());

        Optional<Account> byAccountName = accountService.findByAccountName(eventToAccount.getAccountName());

        if (byAccountName.isPresent()) {
            Account account = byAccountName.get();

            Event eventTobeSaved = new Event(eventToAccount.getEventName(), account);
            account.getEvents().add(eventTobeSaved);

            //AccountDto accountDto = accountService.saveAccount(account);
            EventDto eventDto = eventService.saveEvent(eventTobeSaved);
            log.info("{} -- {}: ",account, eventDto);

            //AccountDto accountDto = accountService.saveAccount(account);
            //log.info("Account: {} was updated.", account);

            return ResponseEntity.ok(eventDto);
        } else {
            return ResponseEntity.badRequest().build(); //responde that account was not found
        }


    }
}
