package com.integu.basic.spring.services;

import com.integu.basic.spring.api.ResultObj;
import com.integu.basic.spring.dto.BankDto;
import com.integu.basic.spring.models.Bank;

import java.util.List;
import java.util.Optional;

public interface BankService {
    ResultObj<List<BankDto>> findAllBanks();

    ResultObj<Bank> saveBank(BankDto bank);

    ResultObj<Bank> editBank(long bankId, BankDto bank);

    ResultObj<BankDto> findBankById(long bankId);

    ResultObj<Long> deleteBankById(long bankId);

    List<BankDto> searchBankByName(String name);
}
