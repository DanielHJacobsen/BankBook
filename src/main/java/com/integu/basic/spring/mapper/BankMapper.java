package com.integu.basic.spring.mapper;

import com.integu.basic.spring.dto.BankDto;
import com.integu.basic.spring.models.Bank;

import static com.integu.basic.spring.mapper.AccountMapper.mapToAccountDto;

public class BankMapper {

    public static Bank mapToBank(BankDto bankDto) {
        return Bank.builder()
                .id(bankDto.getId())
                .address(bankDto.getAddress())
                .email(bankDto.getEmail())
                .name(bankDto.getName())
                .phone(bankDto.getPhone())
                .created(bankDto.getCreated())
                .modified(bankDto.getModified())
                .imageUrl(bankDto.getImageUrl())
                .rating(bankDto.getRating())
                .accounts(bankDto.getAccounts().stream().map(AccountMapper::mapToAccount).toList())
                .build();
    }

    public static BankDto mapToBankDto(Bank bank) {
        return BankDto.builder()
                .id(bank.getId())
                .address(bank.getAddress())
                .email(bank.getEmail())
                .name(bank.getName())
                .phone(bank.getPhone())
                .created(bank.getCreated())
                .modified(bank.getModified())
                .imageUrl(bank.getImageUrl())
                .rating(bank.getRating())
                .accounts(bank.getAccounts().stream().map(AccountMapper::mapToAccountDto).toList())
                .build();
    }
}
