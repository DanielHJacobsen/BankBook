package com.integu.basic.spring.repository;

import com.integu.basic.spring.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findBankById(long bankId);
    @Query("SELECT b from Bank b WHERE b.name LIKE CONCAT('%', :query, '%')")
    List<Bank> searchBanksByName(String query);
}
