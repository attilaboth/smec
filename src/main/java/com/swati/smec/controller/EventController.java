package com.swati.smec.controller;

import com.swati.smec.entity.Account;
import com.swati.smec.entity.Event;
import com.swati.smec.service.AccountService;
import com.swati.smec.service.EventService;
import com.swati.smec.service.dto.EventDto;
import com.swati.smec.service.dto.EventToAccount;
import com.swati.smec.util.ValidatorUtil;
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

    private final EventService eventService;
    private final AccountService accountService;

    public EventController(EventService eventService, AccountService accountService) {
        this.eventService = eventService;
        this.accountService = accountService;
    }

    @GetMapping(value = "/events", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<EventDto>> getAllAccounts() {
        log.info("Request to getAllAccounts received.");
        try {

            return ResponseEntity.ok(eventService.listAllEvents());

        } catch (Exception ex) {
            log.error("Exception in EventController getAllAccounts(): {}", ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/addEventToAccount",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EventDto> addEventToAccount(@RequestBody EventToAccount eventToAccount) {
        final String accountName = eventToAccount.getAccountName();
        final String eventName = eventToAccount.getEventName();
        log.info("Request to add Event: {} to Account: {}  received.", eventName, accountName);

        if (ValidatorUtil.isNotValidParam(accountName)) {
            log.warn("{} is invalid parameter.", accountName);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (ValidatorUtil.isNotValidParam(eventName)) {
            log.warn("{} is invalid parameter.", eventName);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

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
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception exception) {
            log.error("Exception in EventController addEventToAccount: {}", exception.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
