package com.integu.basic.spring.controller.rest;

import com.integu.basic.spring.dto.BankDto;
import com.integu.basic.spring.services.BankService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BankRestController {
    private static final Logger logger = Logger.getLogger(BankRestController.class.getName());
    private final BankService bankService;
    private final RestUtil util;

    @Autowired
    public BankRestController(BankService bankService, RestUtil util) {
        this.bankService = bankService;
        this.util = util;
    }

    @GetMapping("/banks/json")
    public String getBank() {
        return util.parseResponseToJson(bankService.findAllBanks());
    }

    @GetMapping("/bank/{bankId}/json")
    public String getBank(@PathVariable("bankId") long bankId) {
        return util.parseResponseToJson(bankService.findBankById(bankId));
    }

    @PostMapping("/bank/new/json")
    public String newBank(@RequestBody BankDto bank) {
        return util.parseResponseToJson(bankService.saveBank(bank));
    }

    @PostMapping("/bank/{bankId}/edit/json")
    public String editBank(@PathVariable("bankId") long bankId, @RequestBody BankDto bankDto) {
        return util.parseResponseToJson(bankService.editBank(bankId, bankDto));
    }

    @PostMapping("/bank/{bankId}/delete/json")
    public String deleteBank(@PathVariable("bankId") long bankId) {
        return util.parseResponseToJson(bankService.deleteBankById(bankId));
    }
}
