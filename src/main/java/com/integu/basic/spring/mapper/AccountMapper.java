package com.integu.basic.spring.mapper;

import com.integu.basic.spring.dto.AccountDto;
import com.integu.basic.spring.models.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto) {
        return Account.builder()
                .id(accountDto.getId())
                .name(accountDto.getName())
                .bank(accountDto.getBank())
                .balance(accountDto.getBalance())
                .owner(accountDto.getOwner())
                .currency(accountDto.getCurrency())
                .created(accountDto.getCreated())
                .modified(accountDto.getModified())
                .build();
    }

    public static AccountDto mapToAccountDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .name(account.getName())
                .bank(account.getBank())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .owner(account.getOwner())
                .created(account.getCreated())
                .modified(account.getModified())
                .build();
    }
}
