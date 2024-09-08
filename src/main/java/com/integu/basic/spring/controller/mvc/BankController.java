package com.integu.basic.spring.controller.mvc;

import com.integu.basic.spring.dto.BankDto;
import com.integu.basic.spring.models.Bank;
import com.integu.basic.spring.services.BankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BankController {
    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping({"/banks", "/"})
    public String listBanks(Model model) {
        List<BankDto> banks = bankService.findAllBanks();
        model.addAttribute("banks", banks);
        return "bank-list";
    }

    @GetMapping("/bank/{bankId}/details")
    public String getBankDetail(@PathVariable("bankId") long bankId, Model model) {
        // Should be handled better...
        BankDto bankDto = bankService.findBankById(bankId).get();

        model.addAttribute("bank", bankDto);
        model.addAttribute("accounts", bankDto.getAccounts());

        return "bank-details";
    }

    @GetMapping("/bank/{bankId}/edit")
    public String getBankEdit(@PathVariable("bankId") long bankId, Model model) {
        Optional<BankDto> bank = bankService.findBankById(bankId);
        model.addAttribute("bank", bank.get());
        return "bank-edit";
    }

    @PostMapping("/bank/{bankId}/edit")
    public String getBankEdit(@Valid @ModelAttribute("bank") BankDto bank, BindingResult result) {
        if (result.hasErrors()) {
            return "bank-edit";
        }
        bankService.saveBank(bank);
        return "redirect:/banks";
    }

    @GetMapping("/bank/new")
    public String newBank(Model model) {
        Bank bank = new Bank();
        model.addAttribute("bank", bank);
        return "bank-new";
    }

    @PostMapping("/acou/new")
    public String newBank(@Valid @ModelAttribute("bank") BankDto bank, BindingResult result) {
        if (result.hasErrors()) {
            return "bank-new";
        }
        bankService.saveBank(bank);
        return "redirect:/banks";
    }

    @PostMapping("/bank/{bankId}/delete")
    public String deleteBank(@PathVariable("bankId") long bankId) {
        bankService.deleteBankById(bankId);
        return "redirect:/banks";
    }

    @GetMapping("/banks/search")
    public String search(@RequestParam(value = "name") String name, Model model) {
        List<BankDto> bankDtos = bankService.searchBankByName(name);
        model.addAttribute("banks", bankDtos);
        return "bank-list";
    }
}
