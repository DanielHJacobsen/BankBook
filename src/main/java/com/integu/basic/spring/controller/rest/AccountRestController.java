package com.integu.basic.spring.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.integu.basic.spring.api.ResultObj;
import com.integu.basic.spring.dto.AccountDto;
import com.integu.basic.spring.dto.TransferDto;
import com.integu.basic.spring.services.AccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.integu.basic.spring.validations.ValidationMessages.FALLBACK_JSON_ERROR;

@RestController
public class AccountRestController {
    private static final Logger logger = Logger.getLogger(AccountRestController.class.getName());

    private final ObjectWriter jsonWriter;

    private final AccountService accountService;

    @Autowired
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        this.jsonWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    @GetMapping("/accounts/json")
    public String getAccount() {
        return parseResponseToJson(accountService.findAllAccounts());
    }

    @GetMapping("/account/{accountId}/json")
    public String getAccount(@PathVariable("accountId") long accountId) {
        return parseResponseToJson(accountService.findAccountById(accountId));
    }

    @PostMapping("/account/{bankId}/new/json")
    public String newAccount(@PathVariable("bankId") long bankId, @RequestBody AccountDto account) {
        return parseResponseToJson(accountService.saveAccount(bankId, account));
    }

    @PostMapping("/account/{accountId}/edit/json")
    public String editAccount(@PathVariable("accountId") long accountId, @RequestBody AccountDto accountDto) {
        return parseResponseToJson(accountService.editAccount(accountId, accountDto));
    }

    @PostMapping("/account/{accountId}/delete/json")
    public String deleteAccount(@PathVariable("accountId") long accountId) {
        return parseResponseToJson(accountService.deleteAccountById(accountId));
    }

    @PostMapping(value = "/account/transfer/json", consumes = "application/json")
    public String getAccountTransferJson(@RequestBody TransferDto transferDto) {
        return parseResponseToJson(accountService.transferFromAccountToAccount(transferDto));
    }

    private String parseResponseToJson(ResultObj<?> resultObj) {
        try {
            return jsonWriter.writeValueAsString(resultObj);
        } catch (JsonProcessingException e) {
            logger.error(e);
            return FALLBACK_JSON_ERROR;
        }
    }
}
