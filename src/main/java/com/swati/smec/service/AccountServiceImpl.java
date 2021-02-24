package com.swati.smec.service;

import com.swati.smec.entity.Account;
import com.swati.smec.repository.AccountRepository;
import com.swati.smec.service.dto.AccountDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final ModelMapper modelMapper = new ModelMapper();

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountDto> getAllAccounts() {
        List<Account> allAccounts = accountRepository.findAll();

        //FIXME: simplify this
        List<AccountDto> allAccountDto = new ArrayList<>();
        for (Account account : allAccounts) {
            AccountDto map = modelMapper.map(account, AccountDto.class);
            allAccountDto.add(map);
        }

        return allAccountDto;
    }

    @Override
    public AccountDto saveAccount(Account accountToBeSaved) {
        Account savedAccount = accountRepository.save(accountToBeSaved);
        return modelMapper.map(savedAccount, AccountDto.class);
    }

    @Override
    public Optional<Boolean> findByAccountName(String accountName) {
        return accountRepository.findByAccountName(accountName);
    }

}
