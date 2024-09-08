package com.integu.basic.spring.services.impl;

import com.integu.basic.spring.dto.AccountDto;
import com.integu.basic.spring.dto.BankDto;
import com.integu.basic.spring.mapper.BankMapper;
import com.integu.basic.spring.models.Bank;
import com.integu.basic.spring.repository.BankRepository;
import com.integu.basic.spring.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.integu.basic.spring.mapper.BankMapper.mapToBank;

@Service
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public List<BankDto> findAllBanks() {
        List<Bank> banks = bankRepository.findAll();
        return banks
                .stream()
                .map(BankMapper::mapToBankDto)
                .collect(Collectors.toList());
    }

    @Override
    public Bank saveBank(BankDto bankDto) {
        Optional<BankDto> bankDtoOpt = findBankById(bankDto.getId());
        List<AccountDto> accounts = new ArrayList<>();
        if (bankDtoOpt.isPresent()) {
            accounts = bankDtoOpt.get().getAccounts();
        }
        bankDto.setAccounts(accounts);
        Bank bank = mapToBank(bankDto);
        return bankRepository.save(bank);
    }

    @Override
    public Optional<BankDto> findBankById(long bankId) {
        Optional<Bank> bank = bankRepository.findBankById(bankId);

        return bank.map(BankMapper::mapToBankDto);
    }

    @Override
    public void deleteBankById(long bankId) {
        bankRepository.deleteById(bankId);
    }

    @Override
    public List<BankDto> searchBankByName(String name) {
        return bankRepository
                .searchBanksByName(name)
                .stream()
                .map(BankMapper::mapToBankDto)
                .toList();
    }
}
