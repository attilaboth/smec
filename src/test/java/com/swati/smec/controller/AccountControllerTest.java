package com.swati.smec.controller;

import com.swati.smec.entity.Account;
import com.swati.smec.entity.Event;
import com.swati.smec.helper.TestDataBuilder;
import com.swati.smec.service.AccountService;
import com.swati.smec.service.dto.AccountDto;
import com.swati.smec.service.dto.EventStat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

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
        LocalDateTime now = LocalDateTime.now();
        Account account = TestDataBuilder.buildAnAccount("TestAccount");
        Set<Event> eventsSetForAccount = new HashSet<>(Arrays.asList(
                TestDataBuilder.buildAnEvent(account, "Synchonization completed", now),
                TestDataBuilder.buildAnEvent(account, "Synchonization started", TestDataBuilder.getTimestampUnitBefore(23, ChronoUnit.HOURS)),
                TestDataBuilder.buildAnEvent(account, "Synchonization started", TestDataBuilder.getTimestampUnitBefore(23, ChronoUnit.HOURS))
                )
        );
        account.getEvents().addAll(eventsSetForAccount);
        List<AccountDto> accountDtoList = new ArrayList<>();
        accountDtoList.add(new ModelMapper().map(account, AccountDto.class));

        //when
        when(accountService.getAllAccounts()).thenReturn(accountDtoList);

        ResponseEntity<List<AccountDto>> result = accountController.getAllAccounts();

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getBody().size());
    }

    @Test
    void testStatisticForAccount() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Account account = TestDataBuilder.buildAnAccount("TestAccount");
        Set<Event> eventsSetForAccount = new HashSet<>(Arrays.asList(
                TestDataBuilder.buildAnEvent(account, "Synchonization completed", now),
                TestDataBuilder.buildAnEvent(account, "Synchonization started", TestDataBuilder.getTimestampUnitBefore(23, ChronoUnit.HOURS)),
                TestDataBuilder.buildAnEvent(account, "Synchonization started", TestDataBuilder.getTimestampUnitBefore(22, ChronoUnit.HOURS))
                )
        );
        account.getEvents().addAll(eventsSetForAccount);
        List<AccountDto> accountDtoList = new ArrayList<>();
        accountDtoList.add(new ModelMapper().map(account, AccountDto.class));

        //when
        when(accountService.findByAccountName(anyString())).thenReturn(Optional.ofNullable(account));

        ResponseEntity<List<EventStat>> statResult = accountController.statisticForAccount("TestAccount");

        //then
        List<EventStat> body = statResult.getBody();
        Assertions.assertNotNull(statResult);
        Assertions.assertEquals(2, body.size());
        Assertions.assertEquals(2, body.get(1).getCount());
    }

}
