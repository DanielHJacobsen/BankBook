package com.integu.basic.spring.controller.rest;

import com.integu.basic.spring.dto.AccountDto;
import com.integu.basic.spring.dto.TransferDto;
import com.integu.basic.spring.services.AccountService;
import com.integu.basic.spring.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountRestController {
    private static final Logger logger = Logger.getLogger(AccountRestController.class.getName());

    private final AccountService accountService;
    private final JsonUtil util;

    @Autowired
    public AccountRestController(AccountService accountService, JsonUtil util) {
        this.accountService = accountService;
        this.util = util;
    }

    @GetMapping("/accounts/json")
    public ResponseEntity<String> getAccount() {
        return util.parseResponseToJson(accountService.findAllAccounts());
    }

    @GetMapping("/account/{accountId}/json")
    public ResponseEntity<String> getAccount(@PathVariable("accountId") long accountId) {
        return util.parseResponseToJson(accountService.findAccountById(accountId));
    }

    @PostMapping("/account/{bankId}/new/json")
    public ResponseEntity<String> newAccount(@PathVariable("bankId") long bankId, @RequestBody AccountDto account) {
        return util.parseResponseToJson(accountService.newAccount(bankId, account));
    }

    @PostMapping("/account/{accountId}/edit/json")
    public ResponseEntity<String> editAccount(@PathVariable("accountId") long accountId, @RequestBody AccountDto accountDto) {
        return util.parseResponseToJson(accountService.editAccount(accountId, accountDto));
    }

    @PostMapping("/account/{accountId}/delete/json")
    public ResponseEntity<String> deleteAccount(@PathVariable("accountId") long accountId) {
        return util.parseResponseToJson(accountService.deleteAccountById(accountId));
    }

    @PostMapping(value = "/account/transfer/json", consumes = "application/json")
    public ResponseEntity<String> getAccountTransferJson(@RequestBody TransferDto transferDto) {
        return util.parseResponseToJson(accountService.transferFromAccountToAccount(transferDto));
    }
}
