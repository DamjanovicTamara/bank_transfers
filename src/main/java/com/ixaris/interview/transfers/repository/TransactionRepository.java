package com.ixaris.interview.transfers.repository;

import com.ixaris.interview.transfers.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction,Long> {
}
