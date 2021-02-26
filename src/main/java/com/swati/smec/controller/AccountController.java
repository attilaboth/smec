package com.swati.smec.controller;

import com.swati.smec.entity.Account;
import com.swati.smec.service.AccountService;
import com.swati.smec.service.dto.AccountDto;
import com.swati.smec.service.dto.EventStat;
import com.swati.smec.util.EventStatBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        Optional<Account> foundByAccountNameOpt = accountService.findByAccountName(accountName);
        if (foundByAccountNameOpt.isPresent()) {

            List<EventStat> eventStatsList = EventStatBuilder.buildResponseStatisticForAccount(foundByAccountNameOpt.get().getEvents());


            return ResponseEntity.ok(eventStatsList); //FIXME: handle

        }
        return ResponseEntity.ok(new ArrayList<>()); //FIXME: handle

    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/addAccount", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AccountDto> addAccount(@RequestParam(required = true) String accountName) {
        //FIXME: check if it is a whitespace , or only numbers.. etc
        log.info(" addAccount() was called with param: {}", accountName);

        Optional<Account> byAccountName = accountService.findByAccountName(accountName);

        if (byAccountName.isPresent()) {
            log.info("Account: {} already exists.", accountName);
            return ResponseEntity.ok().build();
        } else {
            AccountDto savedAccount = accountService.saveAccount(new Account(accountName));
            log.info("Account: {} was added to the DB.", savedAccount.getAccountName());

            return ResponseEntity.ok(savedAccount);
        }
    }

}
