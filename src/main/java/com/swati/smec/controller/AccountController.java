package com.swati.smec.controller;

import com.swati.smec.entity.Account;
import com.swati.smec.service.AccountService;
import com.swati.smec.service.dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
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

    @RequestMapping(method = RequestMethod.POST,
            value = "/addAccount", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AccountDto> addAccount(@RequestParam(required = true) String accountName) {
        //FIXME: check if it is a whitespace , or only numbers.. etc
        log.info(" addAccount() was called with param: {}", accountName);

        Optional<Boolean> accountNameCheckOpt = accountService.findByAccountName(accountName);
        if (accountNameCheckOpt.isPresent()) {
            log.info("Account: {} already exists.", accountName);
            return ResponseEntity.ok().build();
        } else {
            AccountDto savedAccount = accountService.saveAccount(new Account(accountName));
            log.info("Account: {} was added to the DB.", savedAccount.getAccountName());

            return ResponseEntity.ok(savedAccount);
        }

    }

}
