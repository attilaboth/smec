package com.swati.smec.controller;

import com.swati.smec.entity.Account;
import com.swati.smec.entity.Event;
import com.swati.smec.service.AccountService;
import com.swati.smec.service.EventService;
import com.swati.smec.service.dto.EventDto;
import com.swati.smec.service.dto.EventToAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        log.info("Request to getAllAccounts received");

        try {
            List<EventDto> events = eventService.listAllEvents();
            return ResponseEntity.ok(events);

        } catch (Exception ex) {
            log.error("Exception in EventController getAllAccounts(): {}", ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Exception", "Exception occurred in EventController getAllAccounts(). Check log files.")
                    .build();
        }
    }

    @PostMapping(value = "/addEventToAccount",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EventDto> addEventToAccount(@RequestBody EventToAccount eventToAccount) {
        //TODO: take care of empty, space, and any illegal chars or numbers
        final String accountName = eventToAccount.getAccountName();
        final String eventName = eventToAccount.getEventName();
        log.info("Request to add Event: {} to Account: {}  received.", eventName, accountName);

        try {
            Optional<Account> byAccountName = accountService.findByAccountName(accountName);

            if (byAccountName.isPresent()) {
                Account account = byAccountName.get();
                log.info("Account: {} found.", account.getAccountName());

                Event eventTobeSaved = new Event(eventName, account);
                account.getEvents().add(eventTobeSaved);

                EventDto eventDto = eventService.saveEvent(eventTobeSaved);
                log.info("Event: {} was saved to Account: {} ", eventDto.getEventName(), eventDto.getAccount().getAccountName());

                return ResponseEntity.status(HttpStatus.CREATED).body(eventDto);
            } else {
                log.warn("{} was not found.", accountName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Note", accountName + " was not found.")
                        .build();
            }
        } catch (Exception exception) {
            log.error("Exception in EventController addEventToAccount: {}", exception.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Exception", "Exception occurred in EventController addEventToAccount. Check log files.")
                    .build();
        }
    }

}
