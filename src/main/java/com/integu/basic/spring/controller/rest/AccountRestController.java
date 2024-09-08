package com.integu.basic.spring.controller.rest;

import com.integu.basic.spring.dto.AccountDto;
import com.integu.basic.spring.dto.TransferDto;
import com.integu.basic.spring.services.AccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountRestController {
    private static final Logger logger = Logger.getLogger(AccountRestController.class.getName());

    private final AccountService accountService;
    private final RestUtil util;

    @Autowired
    public AccountRestController(AccountService accountService, RestUtil util) {
        this.accountService = accountService;
        this.util = util;
    }

    @GetMapping("/accounts/json")
    public String getAccount() {
        return util.parseResponseToJson(accountService.findAllAccounts());
    }

    @GetMapping("/account/{accountId}/json")
    public String getAccount(@PathVariable("accountId") long accountId) {
        return util.parseResponseToJson(accountService.findAccountById(accountId));
    }

    @PostMapping("/account/{bankId}/new/json")
    public String newAccount(@PathVariable("bankId") long bankId, @RequestBody AccountDto account) {
        return util.parseResponseToJson(accountService.saveAccount(bankId, account));
    }

    @PostMapping("/account/{accountId}/edit/json")
    public String editAccount(@PathVariable("accountId") long accountId, @RequestBody AccountDto accountDto) {
        return util.parseResponseToJson(accountService.editAccount(accountId, accountDto));
    }

    @PostMapping("/account/{accountId}/delete/json")
    public String deleteAccount(@PathVariable("accountId") long accountId) {
        return util.parseResponseToJson(accountService.deleteAccountById(accountId));
    }

    @PostMapping(value = "/account/transfer/json", consumes = "application/json")
    public String getAccountTransferJson(@RequestBody TransferDto transferDto) {
        return util.parseResponseToJson(accountService.transferFromAccountToAccount(transferDto));
    }
}
