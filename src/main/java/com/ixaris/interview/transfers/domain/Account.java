package com.ixaris.interview.transfers.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNo;

    private Double balance;

    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL)
    List<Transaction> transactionFrom = new ArrayList<>();

    @OneToMany(mappedBy = "destinationAccount",cascade = CascadeType.ALL)
    List<Transaction> transactionTo = new ArrayList<>();

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        final Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(accountNo, account.accountNo) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNo, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNo=" + accountNo +
                ", balance=" + balance +
                '}';
    }
}
