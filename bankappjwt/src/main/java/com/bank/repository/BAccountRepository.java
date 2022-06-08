package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.model.Baccount;
@Repository
public interface BAccountRepository extends JpaRepository<Baccount, Long> {

}
