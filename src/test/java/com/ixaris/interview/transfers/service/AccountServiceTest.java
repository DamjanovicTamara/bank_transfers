package com.ixaris.interview.transfers.service;

import com.ixaris.interview.transfers.dto.AccountDTO;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    private List<AccountDTO> expectedList;
    private AccountDTO expectedAccount;
    private List<AccountDTO> resultList;
    private AccountDTO resultAccount;
    private String expectedAccountNumber;
    private IOException thrown;

    @BeforeEach
    void setUp() throws IOException {
        expectedList = new ArrayList<>();
        resultList = accountService.getInitialAccountsFromTransaction();
    }

    //Initial account - List from transaction only where source account it 0 in transaction
    @Test
    void accountFromTransactionWhereSourceAccountIsZero() throws IOException {

        expectedAccount = new AccountDTO("112233", BigDecimal.ZERO);

        assertTrue(resultList.contains(expectedAccount));
    }

    //List of initial accounts-> where source account is 0 in transaction
    @Test
    void listOfAccountsWhereSourceAccountIsZero(){
        expectedList.add(new AccountDTO("112233", BigDecimal.ZERO));
        expectedList.add(new AccountDTO("223344", BigDecimal.ZERO));
        expectedList.add(new AccountDTO("334455", BigDecimal.ZERO));

        assertEquals(expectedList,resultList);
    }
    @Test
    void accountListNotEmptyFromTransactionWhereSourceAccountIsNotZero() {

        assertThat(expectedList, is(IsEmptyCollection.empty()));
    }

    //Calculate balance from given transactions
    @Test
    void calculatedBalanceForOneAccountOnly() {

        expectedAccount = new AccountDTO("112233", BigDecimal.valueOf(36.77));
        resultList = accountService.calculateBalancesFromCSV();

        assertThat(resultList,hasItem(expectedAccount));
    }

    @Test
    void calculateAllAccountBalances() {

        expectedList.add(new AccountDTO("112233", BigDecimal.valueOf(36.77)));
        expectedList.add(new AccountDTO("223344", BigDecimal.valueOf(30.122)));
        expectedList.add(new AccountDTO("334455", BigDecimal.valueOf(85.808)));

        resultList = accountService.calculateBalancesFromCSV();

        assertEquals(expectedList, resultList);

    }

    //Bank account with highest balance
    @Test
    void accountWithHighestBalance() {

        expectedAccount = new AccountDTO("334455", BigDecimal.valueOf(85.808));
        resultAccount = accountService.bankAccountWithHighestBalance();

        assertEquals(expectedAccount, resultAccount);

    }
    //Most used account in transactions
    @Test
    void testMostUsedAccountInTransaction() throws IOException {
        expectedAccountNumber="112233";
        String resultAccount = accountService.mostUsedAccountInTransaction();

        assertEquals(expectedAccountNumber,resultAccount);
    }


}