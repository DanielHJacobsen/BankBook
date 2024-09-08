package com.integu.basic.spring.controller.mvc;

import com.integu.basic.spring.api.ResultObj;
import com.integu.basic.spring.dto.AccountDto;
import com.integu.basic.spring.dto.TransferDto;
import com.integu.basic.spring.models.Account;
import com.integu.basic.spring.services.AccountService;
import com.integu.basic.spring.validations.Validation;
import jakarta.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


@Controller
public class AccountController {
    private static final Logger logger = Logger.getLogger(AccountController.class.getName());

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account/{accountId}/edit")
    public String getAccountEdit(@PathVariable("accountId") long accountId, Model model) {
        ResultObj<AccountDto> serviceResult = accountService.findAccountById(accountId);
        AccountDto accountDto = serviceResult.getObject();
        if (serviceResult.getResult().equals(ResultObj.Result.SUCCESS) && accountDto != null) {
            model.addAttribute("account", accountDto);
            return "account-edit";

        } else {
            // Type - Error
            return "error-page";
        }
    }

    @PostMapping("/account/{accountId}/edit")
    public String editAccount(@PathVariable("accountId") long accountId,
                              @Valid @ModelAttribute("account") AccountDto accountDto,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "account-edit";
        }
        ResultObj<AccountDto> serviceResult = accountService.editAccount(accountId, accountDto);
        if (serviceResult.getResult().equals(ResultObj.Result.SUCCESS)) {
            return "redirect:/banks";

        } else if (serviceResult.getResult().equals(ResultObj.Result.VALIDATION)) {
            if (serviceResult.getValidation() == null) {
                logger.error("Result type " + ResultObj.Result.VALIDATION + ", but no validation messages available. " + serviceResult);
                return "error-page";
            }
            Validation validation = serviceResult.getValidation();
            result.addError(new FieldError("transfer", validation.getValue(), validation.getMessage()));
            return "account-edit";
        } else {
            return "error-page";
        }
    }

    @GetMapping("/account/{bankId}/new")
    public String newAccount(@PathVariable("bankId") long bankId, Model model) {
        Account account = new Account();
        model.addAttribute("bankId", bankId);
        model.addAttribute("account", account);
        return "account-new";
    }

    @PostMapping("/account/{bankId}/new")
    public String newAccount(@PathVariable("bankId") long bankId,
                             @Valid @ModelAttribute("account") AccountDto account,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "account-new";
        }
        ResultObj<AccountDto> serviceResult = accountService.saveAccount(bankId, account);
        if (serviceResult.getResult().equals(ResultObj.Result.ERROR)) {
            return "error-page";
        } else if (serviceResult.getResult().equals(ResultObj.Result.VALIDATION)) {
            if (serviceResult.getValidation() == null) {
                logger.error("Result type " + ResultObj.Result.VALIDATION + ", but no validation messages available. " + serviceResult);
                return "error-page";
            }
            Validation validation = serviceResult.getValidation();
            result.addError(new FieldError("transfer", validation.getValue(), validation.getMessage()));
            return "account-new";
        }
        return "redirect:/bank/" + bankId + "/details";
    }

    @PostMapping("/account/{accountId}/delete")
    public String deleteAccount(@PathVariable("accountId") long accountId) {
        ResultObj<Long> resultObj = accountService.deleteAccountById(accountId);
        if (resultObj.getResult().equals(ResultObj.Result.ERROR) || resultObj.getResult().equals(ResultObj.Result.VALIDATION)) {
            return "error-page";
        }
        return "redirect:/banks";
    }

    @GetMapping("/account/{accountId}/transfer")
    public String getAccountTransfer(@PathVariable("accountId") long accountId, Model model) {
        TransferDto transfer = new TransferDto();
        transfer.setFromAccountId(accountId);

        ResultObj<AccountDto> serviceResult = accountService.findAccountById(accountId);
        AccountDto accountDto = serviceResult.getObject();
        if (serviceResult.getResult().equals(ResultObj.Result.SUCCESS) && accountDto != null) {
            model.addAttribute("account", accountDto);
            model.addAttribute("transfer", transfer);
            return "account-transfer";

        } else {
            return "error-page";
        }
    }

    @PostMapping("/account/transfer")
    public String accountTransfer(@ModelAttribute("account") AccountDto accountDto,
                                     @Valid @ModelAttribute("transfer") TransferDto transferDto,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return "account-transfer";
        }

        ResultObj<TransferDto> serviceResult = accountService.transferFromAccountToAccount(transferDto);

        if (serviceResult.getResult().equals(ResultObj.Result.SUCCESS)) {
            return "redirect:/banks";

        } else if (serviceResult.getResult().equals(ResultObj.Result.VALIDATION)) {
            if (serviceResult.getValidation() == null) {
                logger.error("Result type " + ResultObj.Result.VALIDATION + ", but no validation messages available. " + serviceResult);
                return "error-page";
            }
            Validation validation = serviceResult.getValidation();
            result.addError(new FieldError("transfer", validation.getValue(), validation.getMessage()));
            return "account-transfer";
        } else {
            return "error-page";
        }
    }
}
