package com.integu.basic.spring.services;

import com.integu.basic.spring.api.ResultObj;
import com.integu.basic.spring.dto.AccountDto;
import com.integu.basic.spring.dto.TransferDto;

import java.util.List;

public interface AccountService {
    ResultObj<List<AccountDto>> findAllAccounts();

    ResultObj<AccountDto> newAccount(long bankId, AccountDto accountDto);

    ResultObj<AccountDto> findAccountById(long accountId);

    ResultObj<AccountDto> editAccount(long accountId, AccountDto accountDto);

    ResultObj<Long> deleteAccountById(long accountId);

    ResultObj<TransferDto> transferFromAccountToAccount(TransferDto transferDto);
}
