package com.ixaris.interview.transfers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Account class with methods to calculate balance
 */
@Getter
@Setter
@NoArgsConstructor
@Log4j2
public class AccountDTO {

    private String accountNumber;
    private BigDecimal balance;

    public AccountDTO(String accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }


    /**
     * Withdraws money from the bank account.
     *
     * @param amount the amount to withdraw
     */

    public void withdraw(BigDecimal amount) {
        if (0 < balance.compareTo(amount)) {
            this.balance = balance.subtract(amount);
        } else {
            log.info("Withdraw not allowed, amount{}is greater than allowed balance:{}", amount, balance);
        }

    }

    /**
     * Deposit an amount of money in the account => balance = balance + amount
     *
     * @param amount
     */
    public void deposit(BigDecimal amount) {
        this.balance = balance.add(amount);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDTO)) return false;
        final AccountDTO that = (AccountDTO) o;
        return Objects.equals(accountNumber, that.accountNumber) && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, balance);
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
