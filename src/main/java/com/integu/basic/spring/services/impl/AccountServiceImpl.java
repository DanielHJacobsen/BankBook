package com.integu.basic.spring.services.impl;

import com.integu.basic.spring.api.ResultObj;
import com.integu.basic.spring.dto.AccountDto;
import com.integu.basic.spring.dto.TransferDto;
import com.integu.basic.spring.mapper.AccountMapper;
import com.integu.basic.spring.models.Account;
import com.integu.basic.spring.models.Bank;
import com.integu.basic.spring.repository.AccountRepository;
import com.integu.basic.spring.repository.BankRepository;
import com.integu.basic.spring.services.AccountService;
import com.integu.basic.spring.validations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.integu.basic.spring.mapper.AccountMapper.mapToAccount;
import static com.integu.basic.spring.mapper.AccountMapper.mapToAccountDto;
import static com.integu.basic.spring.validations.ValidationMessages.ITEM_DOES_NOT_EXIT;
import static com.integu.basic.spring.validations.ValidationMessages.NOT_ENOUGH_BALANCE_ON_ACCOUNT;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;


    // TODO - Remove dependency to bankRepository - Replace with bankService
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, BankRepository bankRepository) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public List<AccountDto> findAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts
                .stream()
                .map(AccountMapper::mapToAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResultObj<AccountDto> saveAccount(long bankId, AccountDto accountDto) {
        Optional<Bank> bank = bankRepository.findBankById(bankId);

        Account account = mapToAccount(accountDto);

        if (bank.isEmpty()) {
            Validation validation = new Validation("bankId", String.format(ITEM_DOES_NOT_EXIT, "bank", bankId));
            return new ResultObj<>(ResultObj.Result.VALIDATION, null, validation);
        } else {
            account.setBank(bank.get());
            Account savedAccount = accountRepository.save(account);
            AccountDto savedAccountDto = mapToAccountDto(savedAccount);
            return new ResultObj<>(ResultObj.Result.SUCCESS, savedAccountDto, null);
        }
    }

    @Override
    public ResultObj<AccountDto> findAccountById(long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);

        Optional<AccountDto> accountDtoOpt = account.map(AccountMapper::mapToAccountDto);

        if (accountDtoOpt.isPresent()) {
            return new ResultObj<>(ResultObj.Result.SUCCESS, accountDtoOpt.get(), null);
        } else {
            Validation validation = new Validation("accountId", String.format(ITEM_DOES_NOT_EXIT, "account", accountId));
            return new ResultObj<>(ResultObj.Result.VALIDATION, null, validation);
        }
    }

    private Optional<AccountDto> findAccountByIdInternal(long accountId) {
        Optional<Account> bank = accountRepository.findById(accountId);

        return bank.map(AccountMapper::mapToAccountDto);
    }

    @Override
    public ResultObj<Long> deleteAccountById(long accountId) {
        Optional<AccountDto> accountDto = findAccountByIdInternal(accountId);
        if (accountDto.isEmpty()) {
            Validation validation = new Validation("accountId", String.format(ITEM_DOES_NOT_EXIT, "account", accountId));
            new ResultObj<>(ResultObj.Result.VALIDATION, null, validation);
        }
        accountRepository.deleteById(accountId);
        return new ResultObj<>(ResultObj.Result.SUCCESS, accountId, null);
    }


    @Override
    public ResultObj<TransferDto> transferFromAccountToAccount(TransferDto transferDto) {

        Optional<AccountDto> fromAccountOpt = findAccountByIdInternal(transferDto.getFromAccountId());

        if (fromAccountOpt.isEmpty()) {
            Validation valid = new Validation("fromAccountId", String.format(ITEM_DOES_NOT_EXIT, "account", transferDto.getFromAccountId()));
            return new ResultObj<>(ResultObj.Result.VALIDATION, null, valid);
        }

        AccountDto fromAccount = fromAccountOpt.get();

        if (fromAccount.getBalance() < transferDto.getAmount()) {
            Validation valid = new Validation("amount", String.format(NOT_ENOUGH_BALANCE_ON_ACCOUNT, transferDto.getAmount(), fromAccount.getBalance()));
            return new ResultObj<>(ResultObj.Result.VALIDATION, null, valid);
        }

        Optional<AccountDto> toAccountOpt = findAccountByIdInternal(transferDto.getToAccountId());

        if (toAccountOpt.isEmpty()) {
            Validation validation = new Validation("toAccountId", String.format(ITEM_DOES_NOT_EXIT, "account", transferDto.getToAccountId()));
            return new ResultObj<>(ResultObj.Result.VALIDATION, null, validation);
        }

        AccountDto toAccount = toAccountOpt.get();

        fromAccount.setBalance(fromAccount.getBalance() - transferDto.getAmount());
        toAccount.setBalance(toAccount.getBalance() + transferDto.getAmount());

        saveAccount(fromAccount.getBank().getId(), fromAccount);
        saveAccount(toAccount.getBank().getId(), toAccount);

        return new ResultObj<>(ResultObj.Result.SUCCESS, transferDto, null);
    }
}
