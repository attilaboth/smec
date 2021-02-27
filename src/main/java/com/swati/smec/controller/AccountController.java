package com.swati.smec.controller;

import com.swati.smec.entity.Account;
import com.swati.smec.entity.Event;
import com.swati.smec.service.AccountService;
import com.swati.smec.service.EventService;
import com.swati.smec.service.dto.AccountDto;
import com.swati.smec.service.dto.EventStat;
import com.swati.smec.util.EventStatBuilder;
import com.swati.smec.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;
    private final EventService eventService;

    public AccountController(AccountService accountService, EventService eventService) {
        this.accountService = accountService;
        this.eventService = eventService;
    }

    @GetMapping(value = "/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<AccountDto>> getAllAccounts() {

        List<AccountDto> allAccounts = accountService.getAllAccounts();

        return ResponseEntity.ok(allAccounts);
    }

    @GetMapping(value = "/statForAccount", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<EventStat>> statisticForAccount(@RequestParam String accountName) {
        log.info("Request to statisticForAccount received for account {} ", accountName);

        if (ValidatorUtil.isNotValidParam(accountName)) {
            log.warn("{} is invalid parameter.", accountName);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            String validAccountName = accountName.trim();
            Optional<Account> foundByAccountNameOpt = accountService.findByAccountName(validAccountName);

            if (foundByAccountNameOpt.isPresent()) {
                final Account account = foundByAccountNameOpt.get();
                log.info("Account: {} was found.", account.getAccountName());

                return ResponseEntity.ok(EventStatBuilder.buildResponseStatisticForAccount(account.getEvents()));
            } else {
                log.warn("{} was not found.", validAccountName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (Exception ex) {
            log.error("Exception in AccountController statisticForAccount: " + ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/statisticForAccountForLastDays", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<EventStat>> statisticForAccountForLastDays(@RequestParam String accountName) {
        log.info("Request to statisticForAccountForLastDays received for account {} ", accountName);

        if (ValidatorUtil.isNotValidParam(accountName)) {
            log.warn("{} is invalid parameter.", accountName);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            Optional<List<Event>> byDateCreatedIsAfter = eventService.findByDateCreatedIsAfter(LocalDateTime.now().minus(30, ChronoUnit.DAYS));
            if(byDateCreatedIsAfter.isPresent()){
                List<Event> eventList = byDateCreatedIsAfter.get();
                return ResponseEntity.ok(EventStatBuilder.buildResponseStatisticForAccount(eventList, accountName));
            }else{
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
            }

        } catch (Exception ex) {
            log.error("Exception in EventController getEventsForLast30Days(): {}", ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/addAccount", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AccountDto> addAccount(@RequestParam String accountName) {
        log.info("Request to addAccount received for account {} ", accountName);

        if (ValidatorUtil.isNotValidParam(accountName)) {
            log.warn("{} is invalid parameter.", accountName);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            String validAccountName = accountName.trim();
            Optional<Account> byAccountName = accountService.findByAccountName(validAccountName);

            if (byAccountName.isPresent()) {
                log.warn("Account: {} already exists.", validAccountName);
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                AccountDto savedAccount = accountService.saveAccount(new Account(validAccountName));
                log.info("{} was created", savedAccount);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
            }

        } catch (Exception exception) {
            log.error("Exception in AccountController addAccount: {}", exception.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}


