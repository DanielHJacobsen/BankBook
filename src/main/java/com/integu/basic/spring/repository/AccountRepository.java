package com.integu.basic.spring.repository;

import com.integu.basic.spring.models.Account;
import com.integu.basic.spring.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
