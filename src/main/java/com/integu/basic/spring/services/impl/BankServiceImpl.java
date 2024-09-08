package com.integu.basic.spring.services.impl;

import com.integu.basic.spring.api.ResultObj;
import com.integu.basic.spring.dto.BankDto;
import com.integu.basic.spring.mapper.BankMapper;
import com.integu.basic.spring.models.Bank;
import com.integu.basic.spring.repository.BankRepository;
import com.integu.basic.spring.services.s2s.BankS2S;
import com.integu.basic.spring.services.BankService;
import com.integu.basic.spring.validations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.integu.basic.spring.mapper.BankMapper.mapToBank;
import static com.integu.basic.spring.validations.ValidationMessages.ID_OF_PATH_AND_BODY_DOES_NOT_MATCH;
import static com.integu.basic.spring.validations.ValidationMessages.ITEM_DOES_NOT_EXIT;

@Service
public class BankServiceImpl implements BankService, BankS2S {
    private final BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public ResultObj<List<BankDto>> findAllBanks() {
        List<Bank> banks = bankRepository.findAll();
        List<BankDto> bankDtos = banks
                .stream()
                .map(BankMapper::mapToBankDto)
                .collect(Collectors.toList());
        return new ResultObj<>(ResultObj.Result.SUCCESS, bankDtos, null);
    }

    @Override
    public ResultObj<Bank> saveBank(BankDto bankDto) {
        bankDto.setAccounts(new ArrayList<>());
        Bank savedBank = bankRepository.save(mapToBank(bankDto));
        return new ResultObj<>(ResultObj.Result.SUCCESS, savedBank, null);
    }

    @Override
    public ResultObj<Bank> editBank(long bankId, BankDto bankDto) {
        if (bankId != bankDto.getId()) {
            Validation validation = new Validation("bank", String.format(ID_OF_PATH_AND_BODY_DOES_NOT_MATCH, bankId, bankDto.getId()));
            return new ResultObj<>(ResultObj.Result.VALIDATION, null, validation);
        }

        Optional<BankDto> currentBank = findBankByIdInternal(bankId);
        if (currentBank.isEmpty()) {
            Validation validation = new Validation("bank", String.format(ITEM_DOES_NOT_EXIT, "bank", bankId));
            return new ResultObj<>(ResultObj.Result.VALIDATION, null, validation);
        }

        bankDto.setAccounts(new ArrayList<>());
        Bank savedBank = bankRepository.save(mapToBank(bankDto));
        return new ResultObj<>(ResultObj.Result.SUCCESS, savedBank, null);
    }

    @Override
    public ResultObj<BankDto> findBankById(long bankId) {
        Optional<BankDto> bankDtoOpt = findBankByIdInternal(bankId);

        if (bankDtoOpt.isPresent()) {
            return new ResultObj<>(ResultObj.Result.SUCCESS, bankDtoOpt.get(), null);
        } else {
            Validation validation = new Validation("bankId", String.format(ITEM_DOES_NOT_EXIT, "bank", bankId));
            return new ResultObj<>(ResultObj.Result.VALIDATION, null, validation);
        }
    }

    @Override
    public Optional<BankDto> findBankByIdInternal(long bankId) {
        Optional<Bank> bank = bankRepository.findBankById(bankId);

        return bank.map(BankMapper::mapToBankDto);
    }

    @Override
    public ResultObj<Long> deleteBankById(long bankId) {
        Optional<BankDto> bankDtoOpt = findBankByIdInternal(bankId);
        if (bankDtoOpt.isEmpty()) {
            Validation validation = new Validation("bankId", String.format(ITEM_DOES_NOT_EXIT, "bank", bankId));
            return new ResultObj<>(ResultObj.Result.VALIDATION, null, validation);
        }

        bankRepository.deleteById(bankId);
        return new ResultObj<>(ResultObj.Result.VALIDATION, bankId, null);
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
