package com.ixaris.interview.transfers.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="source_account")
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="destination_account")
    private Account destinationAccount;


    private Double amount;

    private LocalDateTime transferDate;

 /*   @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)*/


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        final Transaction that = (Transaction) o;
        return Objects.equals(transferId, that.transferId) && Objects.equals(sourceAccount, that.sourceAccount) && Objects.equals(destinationAccount, that.destinationAccount) && Objects.equals(amount, that.amount) && Objects.equals(transferDate, that.transferDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transferId, sourceAccount, destinationAccount, amount, transferDate);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transferId=" + transferId +
                ", sourceAccount=" + sourceAccount +
                ", destinationAccount=" + destinationAccount +
                ", amount=" + amount +
                ", transferDate=" + transferDate +
                '}';
    }
}
