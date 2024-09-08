package com.integu.basic.spring.services;

import com.integu.basic.spring.api.ResultObj;
import com.integu.basic.spring.dto.AccountDto;
import com.integu.basic.spring.dto.TransferDto;
import com.integu.basic.spring.models.Account;

import java.util.List;

public interface AccountService {
    List<AccountDto> findAllAccounts();

    ResultObj<AccountDto> saveAccount(long bankId, AccountDto accountDto);

    ResultObj<AccountDto> findAccountById(long accountNumber);

    ResultObj<Long> deleteAccountById(long accountId);

    ResultObj<TransferDto> transferFromAccountToAccount(TransferDto transferDto);

}
