package com.swati.smec.controller;

import com.swati.smec.entity.Account;
import com.swati.smec.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Account>> getAllAccounts() {

        List<Account> allAccounts = accountService.getAllAccounts();

        return ResponseEntity.ok(allAccounts);
    }

}
