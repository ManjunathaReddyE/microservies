package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.model.TxnHistory;

public interface TxnHistoryRepository extends JpaRepository<TxnHistory, Long> {

}
