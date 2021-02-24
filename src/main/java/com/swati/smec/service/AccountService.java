package com.swati.smec.service;

import com.swati.smec.entity.Account;
import com.swati.smec.service.dto.AccountDto;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<AccountDto> getAllAccounts();

    AccountDto saveAccount(Account accountToBeSaved);

    Optional<Boolean> findByAccountName(String accountName);
}
