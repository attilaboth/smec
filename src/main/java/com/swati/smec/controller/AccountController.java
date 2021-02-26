package com.swati.smec.controller;

import com.swati.smec.entity.Account;
import com.swati.smec.service.AccountService;
import com.swati.smec.service.dto.AccountDto;
import com.swati.smec.service.dto.EventStat;
import com.swati.smec.util.EventStatBuilder;
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
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<AccountDto>> getAllAccounts() {

        List<AccountDto> allAccounts = accountService.getAllAccounts();

        return ResponseEntity.ok(allAccounts);
    }

    @GetMapping(value = "/statForAccount", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<EventStat>> statisticForAccount(@RequestParam(required = true) String accountName) {
        log.debug("Request to statisticForAccount received for account {} ", accountName);

        try {
            Optional<Account> foundByAccountNameOpt = accountService.findByAccountName(accountName);

            if (foundByAccountNameOpt.isPresent()) {
                final Account account = foundByAccountNameOpt.get();
                log.debug("{} is found.", account.getAccountName());
                List<EventStat> eventStatsList = EventStatBuilder.buildResponseStatisticForAccount(account.getEvents());

                log.debug("eventStatsList: {}", eventStatsList);
                return ResponseEntity.ok(eventStatsList);
            }

        } catch (Exception e) {
            log.error("Exception in AccountController.statisticForAccount: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(value = "/addAccount", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AccountDto> addAccount(@RequestParam(required = true) String accountName) {
        //TODO: take care of empty, space, and any illegal chars or numbers
        log.debug("Request to addAccount received for account {} ", accountName);

        try {
            Optional<Account> byAccountName = accountService.findByAccountName(accountName);

            if (byAccountName.isPresent()) {
                log.info("Account: {} already exists.", accountName);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .header("Note", accountName + " already exists.")
                        .build();
            } else {
                AccountDto savedAccount = accountService.saveAccount(new Account(accountName));
                log.info("{} was created", savedAccount);

                return ResponseEntity.status(HttpStatus.CREATED)
                        .header("Note", savedAccount.getAccountName() + " account was created.")
                        .body(savedAccount);
            }

        } catch (Exception exception) {
            log.error("Exception in AccountController.addAccount: {}", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Exception", exception.getMessage())
                    .build();
        }
    }

}
