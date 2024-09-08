package com.integu.basic.spring.services;

import com.integu.basic.spring.dto.BankDto;
import com.integu.basic.spring.models.Bank;

import java.util.List;
import java.util.Optional;

public interface BankService {
    List<BankDto> findAllBanks();

    Bank saveBank(BankDto bank);

    Optional<BankDto> findBankById(long bankId);

    void deleteBankById(long bankId);

    List<BankDto> searchBankByName(String name);
}
