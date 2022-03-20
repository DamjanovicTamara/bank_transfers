package com.ixaris.interview.transfers.service;

import com.ixaris.interview.transfers.dto.AccountDTO;
import com.ixaris.interview.transfers.dto.TransactionDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service to retrieve account balance related operations from transaction
 */
@Service
@Log4j2
public class AccountService {

    private List<AccountDTO> accountsFromCSV = new ArrayList<>();
    private List<AccountDTO> accountsWithBalance = new ArrayList<>();

    private final TransactionService transactionService;

    @Autowired
    public AccountService(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Method to initialize accountDTO objects from transaction where source account is 0, setting balance to O
     *
     * @return List of account DTO objects initialized from a file
     * @throws IOException
     */
    public List<AccountDTO> getInitialAccountsFromTransaction() throws IOException {
        accountsFromCSV = new ArrayList<>();

        final List<TransactionDTO> transactionOfInitialAccount = transactionService.getTransactionStream().
                filter(t -> t.getSourceAccount().equals("0")).collect(Collectors.toList());
        transactionOfInitialAccount.forEach(s -> {
            AccountDTO account =
                    new AccountDTO(s.getDestinationAccount(), BigDecimal.ZERO);
            accountsFromCSV.add(account);
        });
        return accountsFromCSV;
    }

    /**
     * Method which check each transaction and calculate balance from source and destination
     * account using withdraw and deposit methods respectively
     *
     * @return List of accountDTO objects with calculated balance, after all transaction from a file
     */
    public List<AccountDTO> calculateBalancesFromCSV() {
        accountsWithBalance = new ArrayList<>();
        accountsFromCSV.forEach(accountDTO -> {
            try {
                transactionService.getAllTransaction().stream().
                        filter(trans -> trans.getSourceAccount().equals(accountDTO.getAccountNumber())
                                || trans.getDestinationAccount().equals(accountDTO.getAccountNumber()))
                        .forEach(transactionDTO -> {
                            if (transactionDTO.getDestinationAccount().equals(accountDTO.getAccountNumber())) {
                                accountDTO.deposit(BigDecimal.valueOf(Double.parseDouble(transactionDTO.getAmountAsString())));

                            } else {
                                accountDTO.withdraw(BigDecimal.valueOf(Double.parseDouble(transactionDTO.getAmountAsString())));

                            }

                        });
            } catch (IOException e) {
                e.printStackTrace();
            }

            log.info("Calculated Balance for account number:" + accountDTO.getAccountNumber() +
                    " ,balance: " + accountDTO.getBalance());
            accountsWithBalance.add(accountDTO);
        });
        return accountsWithBalance;
    }

    /**
     * @return account which has highest balance, after calculation of all transactions
     */
    public AccountDTO bankAccountWithHighestBalance() {

        return accountsWithBalance.stream().max(Comparator.comparing(AccountDTO::getBalance)).
                orElseThrow(NoSuchElementException::new);

    }

    /**
     * Method to map accounts used in transaction which have source account specified and count them
     *
     * @return account number which is used mostly in transaction
     * @throws IOException
     */
    public String mostUsedAccountInTransaction() throws IOException {
        Map<String, Long> accountsInTransactions =
                transactionService.getTransactionStream().filter(a -> !a.getSourceAccount().equals("0")).flatMap(e -> Stream.of(e.getSourceAccount()))
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return accountsInTransactions.entrySet().stream().max(Comparator.comparingLong(Map.Entry::getValue)).map(Map.Entry::getKey).orElse(null);
    }


}
