package com.ixaris.interview.transfers.dto;

import com.ixaris.interview.transfers.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountDTOTest {
    private  AccountDTO accountDTO;
    private AccountDTO testAccount;
    @BeforeEach
    void setUp() {
         accountDTO = new AccountDTO();
         testAccount = new AccountDTO();
    }

    @Test
    void addingMoneyToZeroBalance(){
        //accountService.getInitialAccountsFromtransaction()
        accountDTO.setBalance(BigDecimal.ZERO);
        accountDTO.deposit(BigDecimal.valueOf(25));
        assertEquals(BigDecimal.valueOf(25),accountDTO.getBalance());
    }

    //Withdraw money when balance is greater than withdraw amount
    @Test
    void withdrawMoneyWhenAllowed(){
        accountDTO.setBalance(BigDecimal.valueOf(20));
        accountDTO.withdraw(BigDecimal.valueOf(10));

        assertEquals(BigDecimal.valueOf(10),accountDTO.getBalance());
    }

    //NOT SURE IF THIS TEST IS CORRECT
    @Test
    void withdrawMoneyWhenNotAllowed(){
        accountDTO.setBalance(BigDecimal.valueOf(10));
        accountDTO.withdraw(BigDecimal.valueOf(20));

        assertEquals(BigDecimal.valueOf(10),accountDTO.getBalance());
    }
    //Not sure if needed
    @Test
    void correspondingBalancesInTransfer(){
        accountDTO.setBalance(BigDecimal.valueOf(20));
        testAccount.setBalance(BigDecimal.valueOf(30));
        accountDTO.withdraw(BigDecimal.valueOf(5));
        testAccount.deposit(BigDecimal.valueOf(5));

        assertEquals(BigDecimal.valueOf(15),accountDTO.getBalance());
        assertEquals(BigDecimal.valueOf(35),testAccount.getBalance());
    }
}