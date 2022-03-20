package com.ixaris.interview.transfers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

/**
 * Transaction class holds values read from CVS file
 */
@Getter
@Setter
@ToString
public class TransactionDTO {

    private final String sourceAccount;
    private final String destinationAccount;
    private final String amountAsString;
    private final String date;
    private final String transferId;


    /**
     * @param sourceAccount
     * @param destinationAccount
     * @param amountAsString
     * @param date
     * @param transferId
     */

    public TransactionDTO(String sourceAccount, String destinationAccount, String amountAsString, String date, String transferId) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amountAsString = amountAsString;
        this.date = date;
        this.transferId = transferId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionDTO)) return false;
        final TransactionDTO that = (TransactionDTO) o;
        return Objects.equals(sourceAccount, that.sourceAccount) && Objects.equals(destinationAccount, that.destinationAccount) && Objects.equals(amountAsString, that.amountAsString) && Objects.equals(date, that.date) && Objects.equals(transferId, that.transferId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceAccount, destinationAccount, amountAsString, date, transferId);
    }
}
