package com.swati.smec.service;

import com.swati.smec.entity.Account;
import com.swati.smec.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account addAccount(Account newAccount) {
        Account savedAccount = accountRepository.save(newAccount);
        return savedAccount;
    }
}
