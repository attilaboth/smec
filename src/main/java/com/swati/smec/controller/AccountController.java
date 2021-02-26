package com.swati.smec.controller;

import com.swati.smec.entity.Account;
import com.swati.smec.service.AccountService;
import com.swati.smec.service.dto.AccountDto;
import com.swati.smec.service.dto.EventStat;
import com.swati.smec.util.EventStatBuilder;
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
        log.info("Request to statisticForAccount received for account {} ", accountName);

        if (ValidatorUtil.isNotValidParam(accountName))
            return respondWithBadRequestEventStat(accountName, HttpStatus.BAD_REQUEST);

        try {
            String validAccountName = accountName.trim();
            Optional<Account> foundByAccountNameOpt = accountService.findByAccountName(validAccountName);

            if (foundByAccountNameOpt.isPresent()) {
                final Account account = foundByAccountNameOpt.get();
                log.info("Account: {} was found.", account.getAccountName());

                return ResponseEntity.ok(EventStatBuilder.buildResponseStatisticForAccount(account.getEvents()));
            } else {
                log.warn("{} was not found.", validAccountName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Note", validAccountName + " was not found.")
                        .build();
            }

        } catch (Exception ex) {
            log.error("Exception in AccountController statisticForAccount: " + ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Exception", "Exception occurred in AccountController statisticForAccount. Check log files.")
                    .build();
        }
    }

    @PostMapping(value = "/addAccount", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AccountDto> addAccount(@RequestParam(required = true) String accountName) {
        log.info("Request to addAccount received for account {} ", accountName);

        if (ValidatorUtil.isNotValidParam(accountName))
            return respondWithBadRequestAccountDto(accountName, HttpStatus.BAD_REQUEST);

        try {
            String validAccountName = accountName.trim();
            Optional<Account> byAccountName = accountService.findByAccountName(validAccountName);

            if (!byAccountName.isPresent()) {
                AccountDto savedAccount = accountService.saveAccount(new Account(validAccountName));
                log.info("{} was created", savedAccount);

                return ResponseEntity.status(HttpStatus.CREATED)
                        .header("Note", savedAccount.getAccountName() + " account was created.")
                        .body(savedAccount);
            } else {
                log.warn("Account: {} already exists.", validAccountName);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .header("Note", validAccountName + " already exists.")
                        .build();
            }

        } catch (Exception exception) {
            log.error("Exception in AccountController addAccount: {}", exception.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Exception", "Exception occurred in AccountController addAccount. Check log files.")
                    .build();
        }
    }

    private ResponseEntity<AccountDto> respondWithBadRequestAccountDto(String invalidParam, HttpStatus httpStatus) {
        log.warn("{} is invalid parameter.", invalidParam);
        return ResponseEntity.status(httpStatus)
                .header("Note", invalidParam + " is invalid parameter.")
                .build();
    }

    //FIXME
    private ResponseEntity<List<EventStat>> respondWithBadRequestEventStat(String invalidParam, HttpStatus httpStatus) {
        log.warn("{} is invalid parameter.", invalidParam);
        return ResponseEntity.status(httpStatus)
                .header("Note", invalidParam + " is invalid parameter.")
                .build();
    }

}


