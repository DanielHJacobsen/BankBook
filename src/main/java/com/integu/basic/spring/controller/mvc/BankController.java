package com.integu.basic.spring.controller.mvc;

import com.integu.basic.spring.api.ResultObj;
import com.integu.basic.spring.dto.BankDto;
import com.integu.basic.spring.models.Bank;
import com.integu.basic.spring.services.BankService;
import com.integu.basic.spring.validations.Validation;
import jakarta.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BankController {
    private static final Logger logger = Logger.getLogger(AccountController.class.getName());

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping({"/banks", "/"})
    public String listBanks(Model model) {
        ResultObj<List<BankDto>> serviceResult = bankService.findAllBanks();
        if (serviceResult.getResult().equals(ResultObj.Result.ERROR) ||
                serviceResult.getResult().equals(ResultObj.Result.VALIDATION) ||
                serviceResult.getObject() == null) {
            return "error-page";

        }
        List<BankDto> banks = serviceResult.getObject();
        model.addAttribute("banks", banks);
        return "bank-list";
    }

    @GetMapping("/bank/{bankId}/details")
    public String getBankDetail(@PathVariable("bankId") long bankId, Model model) {
        ResultObj<BankDto> serviceResult = bankService.findBankById(bankId);

        if (serviceResult.getResult().equals(ResultObj.Result.ERROR) ||
                serviceResult.getResult().equals(ResultObj.Result.VALIDATION) ||
                serviceResult.getObject() == null) {
            return "error-page";

        }

        model.addAttribute("bank", serviceResult.getObject());
        model.addAttribute("accounts", serviceResult.getObject().getAccounts());

        return "bank-details";
    }

    @GetMapping("/bank/{bankId}/edit")
    public String getBankEdit(@PathVariable("bankId") long bankId, Model model) {
        ResultObj<BankDto> serviceResult = bankService.findBankById(bankId);
        if (serviceResult.getResult().equals(ResultObj.Result.ERROR) ||
                serviceResult.getResult().equals(ResultObj.Result.VALIDATION) ||
                serviceResult.getObject() == null) {
            return "error-page";

        }

        model.addAttribute("bank", serviceResult.getObject());
        return "bank-edit";
    }

    @PostMapping("/bank/{bankId}/edit")
    public String getBankEdit(@PathVariable("bankId") long bankId,
                              @Valid @ModelAttribute("bank") BankDto bank,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "bank-edit";
        }
        ResultObj<Bank> serviceResult = bankService.editBank(bankId, bank);
        if (serviceResult.getResult().equals(ResultObj.Result.ERROR)) {
            return "error-page";

        } else if (serviceResult.getResult().equals(ResultObj.Result.VALIDATION)) {
            if (serviceResult.getValidation() == null) {
                logger.error("Result type " + ResultObj.Result.VALIDATION + ", but no validation messages available. " + serviceResult);
                return "error-page";
            }
            Validation validation = serviceResult.getValidation();
            result.addError(new FieldError("bank", validation.getValue(), validation.getMessage()));
            return "bank-edit";
        }
        return "redirect:/banks";
    }

    @GetMapping("/bank/new")
    public String newBank(Model model) {
        Bank bank = new Bank();
        model.addAttribute("bank", bank);
        return "bank-new";
    }

    @PostMapping("/bank/new")
    public String newBank(@Valid @ModelAttribute("bank") BankDto bank, BindingResult result) {
        if (result.hasErrors()) {
            return "bank-new";
        }
        bankService.saveBank(bank);
        return "redirect:/banks";
    }

    @PostMapping("/bank/{bankId}/delete")
    public String deleteBank(@PathVariable("bankId") long bankId) {
        ResultObj<Long> resultObj = bankService.deleteBankById(bankId);
        if (resultObj.getResult().equals(ResultObj.Result.ERROR) || resultObj.getResult().equals(ResultObj.Result.VALIDATION)) {
            return "error-page";
        }
        return "redirect:/banks";
    }

    @GetMapping("/banks/search")
    public String search(@RequestParam(value = "name") String name, Model model) {
        List<BankDto> bankDtos = bankService.searchBankByName(name);
        model.addAttribute("banks", bankDtos);
        return "bank-list";
    }
}
