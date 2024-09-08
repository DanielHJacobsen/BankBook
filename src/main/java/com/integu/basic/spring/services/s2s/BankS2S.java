package com.integu.basic.spring.services.s2s;

import com.integu.basic.spring.dto.BankDto;

import java.util.Optional;

public interface BankS2S {

    Optional<BankDto> findBankByIdInternal(long bankId);
}
