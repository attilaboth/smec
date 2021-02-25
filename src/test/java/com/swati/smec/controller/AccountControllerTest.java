package com.swati.smec.controller;

import com.swati.smec.entity.Account;
import com.swati.smec.entity.Event;
import com.swati.smec.service.AccountService;
import com.swati.smec.service.dto.AccountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    AccountService accountService;

    @InjectMocks
    AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAccounts() {
        //given
        List<Event> eventsList = Arrays.asList(new Event("eventName", new Account("accountName")));
        HashSet<Event> eventHashSet = new HashSet<>(eventsList);
        List<AccountDto> accountDtos = Arrays.asList(new AccountDto("accountName", eventHashSet));

        //when
        when(accountService.getAllAccounts()).thenReturn(accountDtos);

        ResponseEntity<List<AccountDto>> result = accountController.getAllAccounts();

        //then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(accountDtos, result.getBody());
    }

    //@Test
    void testAddAccount() {
        when(accountService.saveAccount(any())).thenReturn(new AccountDto("accountName", new HashSet<Event>(Arrays.asList(new Event("eventName", new Account("accountName"))))));
        when(accountService.findByAccountName(anyString())).thenReturn(null);

        ResponseEntity<AccountDto> result = accountController.addAccount("accountName");
        assertEquals(null, result);
    }
}
