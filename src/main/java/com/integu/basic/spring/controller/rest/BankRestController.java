package com.integu.basic.spring.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.integu.basic.spring.api.ResultObj;
import com.integu.basic.spring.dto.BankDto;
import com.integu.basic.spring.services.BankService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.integu.basic.spring.validations.ValidationMessages.FALLBACK_JSON_ERROR;

@RestController
public class BankRestController {
    private static final Logger logger = Logger.getLogger(BankRestController.class.getName());

    private final ObjectWriter jsonWriter;

    private final BankService bankService;

    @Autowired
    public BankRestController(BankService bankService) {
        this.bankService = bankService;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        this.jsonWriter = mapper.writer().withDefaultPrettyPrinter();
    }

    @GetMapping("/banks/json")
    public String getBank() {
        return parseResponseToJson(bankService.findAllBanks());
    }

    @GetMapping("/bank/{bankId}/json")
    public String getBank(@PathVariable("bankId") long bankId) {
        return parseResponseToJson(bankService.findBankById(bankId));
    }

    @PostMapping("/bank/new/json")
    public String newBank(@RequestBody BankDto bank) {
        return parseResponseToJson(bankService.saveBank(bank));
    }

    @PostMapping("/bank/{bankId}/edit/json")
    public String editBank(@PathVariable("bankId") long bankId, @RequestBody BankDto bankDto) {
        return parseResponseToJson(bankService.editBank(bankId, bankDto));
    }

    @PostMapping("/bank/{bankId}/delete/json")
    public String deleteBank(@PathVariable("bankId") long bankId) {
        return parseResponseToJson(bankService.deleteBankById(bankId));
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
